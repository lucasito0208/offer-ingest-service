package com.lucasdev.offeringestservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucasdev.offeringestservice.dto.ProductOfferDto;
import com.lucasdev.offeringestservice.model.Product;
import com.lucasdev.offeringestservice.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class KafkaConsumerService {

    private final ProductRepository productRepository;
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
}
