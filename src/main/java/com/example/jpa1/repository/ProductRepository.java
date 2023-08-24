package com.example.jpa1.repository;

import com.example.jpa1.entity.ProductEntity;
import com.example.jpa1.product.model.ProductVo;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findAllByName(String name, Sort sort); //네임으로 가져오는 쿼리문을 추가로 만든거임
    List<ProductEntity> findAllByNameContains(String name);

    @Query("select a from ProductEntity a join fetch a.providerEntity join fetch a.productDetailEntity")
    List<ProductEntity> selProductList(); //이거쓰면 계속 셀렉트되는, n+1문제를 해결하게 됨
}
