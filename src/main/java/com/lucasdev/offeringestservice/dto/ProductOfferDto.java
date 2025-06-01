package com.lucasdev.offeringestservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductOfferDto {

    private String productId;
    private String productName;
    private String category;
    private double price;
    private String promotion;
    private List<String> relatedProductIds;

}
