package com.example.asset_management_idnes.repository;

import com.example.asset_management_idnes.domain.PlanList;
import com.example.asset_management_idnes.domain.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {
    boolean existsByType(String name);
}
