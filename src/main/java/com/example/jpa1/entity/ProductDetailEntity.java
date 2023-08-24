package com.example.jpa1.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_detail")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class ProductDetailEntity {

    @Id
    @Column(name = "product_number")
    private Long number; //바로 밑에꺼랑 같은거지만 얘는 쪼인 위한것

    @OneToOne
    @MapsId
    @JoinColumn(name = "product_number", updatable = false, nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private ProductEntity productEntity; //바로 위에꺼랑 같은거지만 얘는 타입 설정 위한 것


    @Column(length = 500)
    private String description;

}

