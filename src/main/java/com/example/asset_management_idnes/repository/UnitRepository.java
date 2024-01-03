package com.example.asset_management_idnes.repository;

import com.example.asset_management_idnes.domain.Currency;
import com.example.asset_management_idnes.domain.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnitRepository extends JpaRepository<Unit, Long> {

    boolean existsByUnitName(String name);

}
