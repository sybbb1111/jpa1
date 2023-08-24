package com.example.jpa1.repository;

import com.example.jpa1.entity.ProductEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findAllByName(String name, Sort sort); //네임으로 가져오는 쿼리문을 추가로 만든거임
    List<ProductEntity> findAllByNameContains(String name);
}
