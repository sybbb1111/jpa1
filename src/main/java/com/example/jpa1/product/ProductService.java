package com.example.jpa1.product;

import com.example.jpa1.entity.ProductDetailEntity;
import com.example.jpa1.entity.ProductEntity;
import com.example.jpa1.entity.ProviderEntity;
import com.example.jpa1.product.model.ProductRegDto;
import com.example.jpa1.product.model.ProductUpdDto;
import com.example.jpa1.product.model.ProductVo;
import com.example.jpa1.repository.ProductDetailRepository;
import com.example.jpa1.repository.ProductRepository;
import com.example.jpa1.repository.ProviderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRep;
    private final ProviderRepository providerRep;
    private final ProductDetailRepository detailRep;

    public ProductVo postProduct(ProductRegDto dto) {
        //원투원 테이블에 인서트하기 (프로덕트 + 프로덕트 디테일)

        ProviderEntity providerEntity = providerRep.findById(dto.getProviderId()).get();
        //프로바이더 이름 가져오기위해 위의 과정


        ProductEntity productEntity = ProductEntity.builder()
                .stock(dto.getStock())
                .price(dto.getPrice())
                .name(dto.getName())
                .providerEntity(providerEntity)
                .build();

        productRep.save(productEntity); //Product 테이블에 인서트

        ProductDetailEntity productDetailEntity = ProductDetailEntity.builder()
                .productEntity(productEntity)
                .description(dto.getDescription())
                .build();
        detailRep.save(productDetailEntity); //ProductDetail 테이블에 인서트

        log.info("productDetail-number: {}", productDetailEntity.getNumber());

        return ProductVo.builder()
                .number(productEntity.getNumber())
                .name(productEntity.getName())
                .price(productEntity.getPrice())
                .stock(productEntity.getStock())
                .providerName(providerEntity.getName())
                .description(productDetailEntity.getDescription())
                .build();




    }

    public ProductEntity updProduct(ProductUpdDto dto) {
        ProductEntity result = productRep.getReferenceById(dto.getNumber());
        result.setPrice(dto.getPrice());
        result.setName(dto.getName());
        result.setStock(dto.getStock());

        return productRep.save(result);

    }

    public List<ProductVo> getProductAll(String name) {
        List<ProductEntity> list = null;

        if(name == null){
            list = productRep.findAll(Sort.by(Sort.Direction.DESC, "number"));
        } else {
            list = productRep.findAllByName(name, Sort.by(Sort.Direction.DESC, "number"));
        }

/*
        List<ProductVo> resultList = new ArrayList<>();
        for(ProductEntity entity : list) {
            resultList.add(ProductVo.builder()
                            .number(entity.getNumber())
                            .name(entity.getName())
                            .price(entity.getPrice())
                            .stock(entity.getStock())
                    .build());
        }
        return resultList;
        */


        return list.stream().map(entity -> ProductVo.builder() //map은 새로만드는 것과 '크기가 똑같을 때'쓴다. (내용만 바꿀 때)
                .number(entity.getNumber())
                .name(entity.getName())
                .price(entity.getPrice())
                .stock(entity.getStock())
                .providerName(entity.getProviderEntity().getName())
                .description(entity.getProductDetailEntity().getDescription())
                .build()
        ).toList();

    }

    public List<ProductVo> getProductAllSearch(String name) {
        List<ProductEntity> list = productRep.findAllByNameContains(name);
        return list.stream().map(entity -> ProductVo.builder()
                .number(entity.getNumber())
                .name(entity.getName())
                .price(entity.getPrice())
                .stock(entity.getStock()).build()
        ).toList();


    }


    public ProductVo getProduct(long number) {
        //ProductEntity entity = productRep.getReferenceById(number); // getById -entity를 리턴하고 없을 경우 예외를 발생시킵니다.
        Optional<ProductEntity> optEntity = productRep.findById(number); //findById - 실제로 데이터베이스에 도달하고 데이터베이스의 행에 대한 실제 개체 매핑을 반환합니다.
        //데이터베이스에 레코드가 없는 경우 null을 반환합니다.
        if(optEntity.isEmpty()) return null; //비어있으면 트루

        ProductEntity entity = optEntity.get();

        return ProductVo.builder()
                .number(entity.getNumber())
                .name(entity.getName())
                .price(entity.getPrice())
                .stock(entity.getStock())
                .build();

    }

    public int delProduct(long number) {

        productRep.deleteById(number);
        return 1;

    }

}
