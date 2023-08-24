package com.example.jpa1.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "product_detail")
@Data
@SuperBuilder
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ProductDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long id;

    @Column(length = 500)
    private String description;



}

