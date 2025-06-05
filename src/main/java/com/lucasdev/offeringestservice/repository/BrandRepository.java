package com.lucasdev.offeringestservice.repository;

import com.lucasdev.offeringestservice.model.Brand;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.Optional;

public interface BrandRepository extends Neo4jRepository<Brand, String> {

    Optional<Brand> findByBrandId(String id);

    @Query("MATCH (b:Brand)-[:OFFERS]->(p:Product) WHERE b.brandId=$brandId RETURN b, collect(p)")
    Optional<Brand> fetchBrandWithProducts(String brandId);



}
