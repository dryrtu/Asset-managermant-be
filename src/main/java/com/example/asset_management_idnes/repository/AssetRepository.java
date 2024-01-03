package com.example.asset_management_idnes.repository;

import com.example.asset_management_idnes.domain.Asset;
import com.example.asset_management_idnes.statics.AssetStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;
@Repository
@Transactional
public interface AssetRepository extends JpaRepository<Asset, Long> {

    Asset findByShoppingPlanId(Long shoppingPlanId);

    @Query("SELECT a FROM Asset a " +
            "INNER JOIN a.shoppingPlan sp " +
            "INNER JOIN sp.planListDetails pld " +
            "INNER JOIN a.contracts c " +
            "INNER JOIN c.suppliers s " +
            "WHERE 1=1 " +
            "AND (:itemCode IS NULL OR LOWER(a.itemCode) LIKE LOWER(:itemCode)) " +
            "AND (:nameAsset IS NULL OR LOWER(a.assetName) LIKE LOWER(:nameAsset)) " +
            "AND (:nameOfPlanList IS NULL OR LOWER(pld.nameOfPlanList) LIKE LOWER(:nameOfPlanList)) " +
            "AND (:supplierName IS NULL OR LOWER(s.supplierName) LIKE LOWER(:supplierName)) " +
            "AND (:licenseDateStart IS NULL OR a.licenceDuration  >= :licenseDateStart) " +
            "AND (:licenseDateEnd IS NULL OR a.licenceDuration  <= :licenseDateEnd) " +
            "AND (:warrantyPeriodStart IS NULL OR c.warrantyStartDate  >= :warrantyPeriodStart) " +
            "AND (:warrantyPeriodEnd IS NULL OR c.warrantyEndDate <= :warrantyPeriodEnd) " +
            "AND ((a.assetStatus = :assetStatus1) " +
            "OR (a.assetStatus = :assetStatus2))")
    Page<Asset> searchAdvanced(
            @Param("itemCode") String itemCode,
            @Param("nameAsset") String nameAsset,
            @Param("nameOfPlanList") String nameOfPlanList,
            @Param("supplierName") String supplierName,
            @Param("licenseDateStart") LocalDate licenseDateStart,
            @Param("licenseDateEnd") LocalDate licenseDateEnd,
            @Param("warrantyPeriodStart") LocalDate warrantyPeriodStart,
            @Param("warrantyPeriodEnd") LocalDate warrantyPeriodEnd,
            @Param("assetStatus1") AssetStatus assetStatus1,
            @Param("assetStatus2") AssetStatus assetStatus2,
            Pageable pageable);

}