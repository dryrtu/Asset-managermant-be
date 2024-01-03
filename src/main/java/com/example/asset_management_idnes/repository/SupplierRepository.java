package com.example.asset_management_idnes.repository;

import com.example.asset_management_idnes.domain.Currency;
import com.example.asset_management_idnes.domain.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    boolean existsBySupplierCode(String name);
    List<Supplier> findBySupplierName(String name);

    List<Supplier> findBySupplierCode(String supplierCode);
}
