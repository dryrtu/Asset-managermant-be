package com.example.asset_management_idnes.repository;

import com.example.asset_management_idnes.domain.AssetUpdatedHistory;
import com.example.asset_management_idnes.domain.ShoppingPlanUpdatedHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AssetUpdatedHistoryRepository extends JpaRepository<AssetUpdatedHistory, Long> {

    @Query("SELECT COALESCE(MAX(pv.version), '00') FROM AssetUpdatedHistory pv WHERE pv.asset.id = :productId")
    String findLatestVersion(@Param("productId") Long productId);
}
