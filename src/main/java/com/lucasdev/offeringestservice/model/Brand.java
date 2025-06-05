package com.lucasdev.offeringestservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Node("Brand")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Brand {

    @Id
    private String brandId;

    private String name;

    @Relationship(type="OFFERS", direction = Relationship.Direction.OUTGOING)
    private List<Product> products;
}
