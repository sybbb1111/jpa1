package com.example.jpa1.product;

import com.example.jpa1.entity.ProductEntity;
import com.example.jpa1.product.model.ProductUpdDto;
import com.example.jpa1.product.model.ProductVo;
import com.example.jpa1.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRep;

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
