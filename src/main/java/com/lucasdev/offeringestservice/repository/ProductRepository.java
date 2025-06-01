package com.lucasdev.offeringestservice.repository;

import com.lucasdev.offeringestservice.model.Product;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

public interface ProductRepository extends Neo4jRepository<Product, String> {

    @Query("""
        MATCH (a:Product {productId: $fromId}), (b:Product {productId: $toId})
        MERGE (a)-[:RELATED_WITH]->(b)
    """)
    void relateProducts(String fromId, String toId);

}
