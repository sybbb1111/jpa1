package com.example.jpa1.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity //이거 엔터티로 쓸거다
@Table(name = "product") //테이블로 쓸거야, 이름 지정
@Data
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true) //내 부모꺼도 투스트링에 찍어라
@EqualsAndHashCode(callSuper = true)
public class ProductEntity extends BaseEntity { //클래스 생성하자마자 빨간줄 뜨는 이유?? 기본키를 설정안해줬기 때문
    @Id //pk
    @GeneratedValue(strategy = GenerationType.IDENTITY) //AutoIncrement
    @Column(updatable = false, nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long number;

    @Column(nullable = false) //null을 허용하지 않겠다.
    private String name;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer stock;

    @ManyToOne(optional = false) //레이지로딩이 기본세팅, inner join으로 변경된다고 하는데 안됨,,
    @JoinColumn(name = "provider_id") //외래키가 걸릴 컬럼명 적기
    private ProviderEntity providerEntity;
    //외래키가 어디에 걸려있는지 생각해보면 된다

    @OneToOne(mappedBy = "productEntity") //맵드바이 써서 컬럼은 새로 안생기고 둘이 양방향으로 연결만 가능하게 됨
    private ProductDetailEntity productDetailEntity;




}
