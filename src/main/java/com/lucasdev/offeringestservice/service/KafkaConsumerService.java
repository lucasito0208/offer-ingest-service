package com.lucasdev.offeringestservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucasdev.offeringestservice.dto.BrandDto;
import com.lucasdev.offeringestservice.dto.ProductOfferDto;
import com.lucasdev.offeringestservice.model.Brand;
import com.lucasdev.offeringestservice.model.Product;
import com.lucasdev.offeringestservice.repository.BrandRepository;
import com.lucasdev.offeringestservice.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class KafkaConsumerService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "productos-promociones-in", groupId = "offer-ingest-group")
    public void consume(ConsumerRecord<String, String> record) {
        try {
            String json = record.value();
            ProductOfferDto dto = objectMapper.readValue(json, ProductOfferDto.class);
            log.info("Mensaje recibido {}", dto);
            Product nodo = Product.builder()
                    .productId(dto.getProductId())
                    .productName(dto.getProductName())
                    .category(dto.getCategory())
                    .price(dto.getPrice())
                    .promotion(dto.getPromotion())
                    .build();
            productRepository.save(nodo);

            if(dto.getRelatedProductIds() !=  null) {
                for (String relatedId : dto.getRelatedProductIds()) {
                    productRepository.relateProducts(dto.getProductId(), relatedId);
                }
            }
            log.info("Producto guardado en grafo: {}", dto.getProductId());
        }catch(Exception ex) {
            log.error("Error procesando el mensaje en Kafka", ex);
        }
    }

    @KafkaListener(topics = "brands-in", groupId = "offer-ingest-group")
    public void consumeBrand(ConsumerRecord<String, String> record) {
        try {
            String json = record.value();
            BrandDto dto = objectMapper.readValue(json, BrandDto.class);
            log.info("Mensaje recibido {}", dto);
            Brand nodo = Brand.builder()
                    .brandId(dto.getBrandId())
                    .name(dto.getName())
                    .build();
            // Tratamiento de la relación entre nodos
            if(dto.getProducts() != null && !dto.getProducts().isEmpty()){
                List<Product> products = productRepository.findAllById(dto.getProducts());
                nodo.setProducts(products);
            }
            brandRepository.save(nodo);
            log.info("Marca procesada {}", nodo);
        }catch (Exception ex) {
            log.error("Error procesando el mensaje en Kafka", ex);
        }
    }


}
