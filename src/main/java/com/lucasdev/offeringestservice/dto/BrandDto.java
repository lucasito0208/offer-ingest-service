package com.lucasdev.offeringestservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class BrandDto {

    private String brandId;
    private String name;
    private List<String> products;
}
