package com.example.asset_management_idnes.repository;

import com.example.asset_management_idnes.domain.PlanList;
import com.example.asset_management_idnes.domain.PlanListDetail;
import com.example.asset_management_idnes.statics.PlanListDetailStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PlanListDetailRepository extends JpaRepository<PlanListDetail, Long> {

    List<PlanListDetail> findAllByPlanList(PlanList planList);


    @Query("select p from PlanListDetail p inner join p.planList sp where sp.id = ?1")
    List<PlanListDetail> findByPlanList(Long idPlanList);

    @Query("SELECT p FROM PlanListDetail p " +
            "INNER JOIN p.planList pl " +
            "WHERE 1=1 " +
            "AND (:year IS NULL OR LOWER(pl.year) LIKE LOWER(:year)) " +
            "AND (:planListID IS NULL OR pl.id = :planListID) " +
            "AND (:nameOfPlanList IS NULL OR LOWER(p.nameOfPlanList) LIKE LOWER(:nameOfPlanList)) " +
            "AND (:createdDateStart IS NULL OR p.createdDate >= :createdDateStart) " +
            "AND (:createdDateEnd IS NULL OR p.createdDate <= :createdDateEnd) " +
            "AND (:approvedDateStart IS NULL OR p.approvedDate >= :approvedDateStart) " +
            "AND (:approvedDateEnd IS NULL OR p.approvedDate <= :approvedDateEnd) " +
            "AND ((p.planListDetailStatus = :planListDetailStatus1) " +
            "OR (p.planListDetailStatus = :planListDetailStatus2) " +
            "OR (p.planListDetailStatus = :planListDetailStatus3) " +
            "OR (p.planListDetailStatus = :planListDetailStatus4))")
    Page<PlanListDetail> searchAdvanced(
            @Param("year") String year,
            @Param("planListID") Long planListID,
            @Param("nameOfPlanList") String nameOfPlanList,
            @Param("createdDateStart") LocalDate createdDateStart,
            @Param("createdDateEnd") LocalDate createdDateEnd,
            @Param("approvedDateStart") LocalDate approvedDateStart,
            @Param("approvedDateEnd") LocalDate approvedDateEnd,
            @Param("planListDetailStatus1") PlanListDetailStatus planListDetailStatus1,
            @Param("planListDetailStatus2") PlanListDetailStatus planListDetailStatus2,
            @Param("planListDetailStatus3") PlanListDetailStatus planListDetailStatus3,
            @Param("planListDetailStatus4") PlanListDetailStatus planListDetailStatus4,
            Pageable pageable);
}
