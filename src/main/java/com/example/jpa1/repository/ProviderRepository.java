package com.example.jpa1.repository;

import com.example.jpa1.entity.ProductEntity;
import com.example.jpa1.entity.ProviderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProviderRepository extends JpaRepository<ProviderEntity, Long> {
}
