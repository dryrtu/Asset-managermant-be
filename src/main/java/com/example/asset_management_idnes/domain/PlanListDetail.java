package com.example.asset_management_idnes.domain;

import com.example.asset_management_idnes.statics.PlanListDetailStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "plan_list_detail")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PlanListDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "nameOfPlanList")
    String nameOfPlanList;  // Tên kế hoạch


    @ManyToMany
    @JoinTable(name = "planList_listDetail",
            joinColumns = @JoinColumn(name = "plan_list_detail_id"),
            inverseJoinColumns = @JoinColumn(name = "plan_list_id"))
    Set<PlanList> planList;

    @Column(name = "plan_list_status")
    PlanListDetailStatus planListDetailStatus;  // Trạng thái kế hoạch

    @Column(name = "approvedBy")
    String approvedBy; // Người phê duyệt

    @Column(name = "created_date")
    LocalDate createdDate; // Ngày tạo

    @Column(name = "approved_date")
    LocalDate approvedDate; // Ngày phê duyệt
}
