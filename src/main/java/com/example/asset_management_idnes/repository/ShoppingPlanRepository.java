package com.example.asset_management_idnes.repository;

import com.example.asset_management_idnes.domain.ShoppingPlan;
import com.example.asset_management_idnes.model.response.ListShoppingPlanResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ShoppingPlanRepository extends JpaRepository<ShoppingPlan, Long> {

//    boolean existsBySerial(String serial);

    @Query("SELECT sp FROM ShoppingPlan sp " +
            "INNER JOIN sp.planListDetails pld " +
            "INNER JOIN pld.planList pl " +
            "INNER JOIN sp.units unt " +
            "INNER JOIN sp.currencies cr " +
            "INNER JOIN sp.productTypeDetails ptd " +
            "INNER JOIN ptd.productTypes pt " +
            "WHERE 1=1 AND (?1 IS NULL OR sp.productName like ?1) " +
            "AND (?2 IS NULL OR pl.id = ?2) " +
            "AND (?3 IS NULL OR pld.id = ?3) " +
            "AND (?4 IS NULL OR unt.id = ?4) " +
            "AND (?5 IS NULL OR cr.id = ?5) " +
            "AND (?6 IS NULL OR pt = ?6) " +
            "AND (?7 IS NULL OR ptd = ?7)")
    Page<ShoppingPlan> searchShoppingPlans(@Param("productName") String productName,
                                           @Param("planListId") Long planListId,
                                           @Param("planListDetailId") Long planListDetailId,
                                           @Param("unitId") Long unitId,
                                           @Param("currencyId") Long currencyId,
                                           @Param("productTypeId") Long productTypeId,
                                           @Param("productTypeDetailId") Long productTypeDetailId,
                                           Pageable pageable);

    // Lấy danh sách khi người dùng chọn năm
    @Query("select sp from ShoppingPlan sp inner join sp.planListDetails pld inner join pld.planList pl where pl.id= ?1 and sp.productName like ?2")
    Page<ShoppingPlan> searchShoppingPlanByPlanListId(@Param("planListId") Long planListId,
                                                      @Param("productName") String productName,
                                                      Pageable pageable);

    // Lấy danh sách sp khi người dùng chọn danh sách
    @Query("select sp from ShoppingPlan sp inner join sp.planListDetails pld where pld.id = ?1 and sp.productName like ?2")
    Page<ShoppingPlan> searchShoppingPlanByPlanListDetailId(@Param("planListDetailId") Long planListDetailId,
                                                            @Param("productName") String productName,
                                                            Pageable pageable);


    @Query("select sp from ShoppingPlan sp inner join sp.planListDetails pl where pl.id = ?1")
    Page<ShoppingPlan> findByPlanListDetails(Long idPlanListDetail, Pageable pageable);

    @Query("select sp from ShoppingPlan sp where sp.productName like ?1")
    Page<ShoppingPlan> searchByProductName(String s, Pageable pageable);

    @Query("select sp from ShoppingPlan sp inner join sp.planListDetails pl where pl.id = ?1")
    List<ShoppingPlan> findByPlanListDetailsNotPagination(Long idPlanListDetail);
}

