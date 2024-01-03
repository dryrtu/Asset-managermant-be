package com.example.asset_management_idnes.repository;

import com.example.asset_management_idnes.domain.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ManufacturerResponsitory extends JpaRepository<Manufacturer, Long> {

    boolean existsByManufacturer(String manufacturer);


}
