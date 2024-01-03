package com.example.asset_management_idnes.repository;

import com.example.asset_management_idnes.domain.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    boolean existsByCurrencyUnit(String name);

}
