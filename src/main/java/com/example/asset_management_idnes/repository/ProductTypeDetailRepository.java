package com.example.asset_management_idnes.repository;

import com.example.asset_management_idnes.domain.PlanList;
import com.example.asset_management_idnes.domain.PlanListDetail;
import com.example.asset_management_idnes.domain.ProductType;
import com.example.asset_management_idnes.domain.ProductTypeDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductTypeDetailRepository extends JpaRepository<ProductTypeDetail, Long> {

    List<ProductTypeDetail> findAllByProductTypes(ProductType productType);

    @Query("select p from ProductTypeDetail p inner join p.productTypes sp where sp.id = ?1")
    List<ProductTypeDetail> findByProductType(Long idProductType);
}
