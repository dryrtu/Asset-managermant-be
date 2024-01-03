package com.example.asset_management_idnes.repository;

import com.example.asset_management_idnes.domain.ShoppingPlanUpdatedHistory;
import com.example.asset_management_idnes.domain.Supplier;
import com.example.asset_management_idnes.model.response.ListVersionUpdateByShoppingPlanResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ShoppingPlanUpdatedHistoryRepository extends JpaRepository<ShoppingPlanUpdatedHistory, Long> {

    @Query("SELECT COALESCE(MAX(pv.version), '00') FROM ShoppingPlanUpdatedHistory pv WHERE pv.shoppingPlan.id = :productId")
    String findLatestVersion(@Param("productId") Long productId);

    @Query("SELECT history FROM ShoppingPlanUpdatedHistory history " +
            "WHERE history.version = :version AND history.shoppingPlan.id = :shoppingPlanId")
    ShoppingPlanUpdatedHistory findByShoppingPlanIdAndVersion(Long shoppingPlanId, String version);

    @Query("SELECT DISTINCT history.version FROM ShoppingPlanUpdatedHistory history WHERE history.shoppingPlan.id = :shoppingPlanId")
    List<String> findVersionsByShoppingPlanId(Long shoppingPlanId);
}
