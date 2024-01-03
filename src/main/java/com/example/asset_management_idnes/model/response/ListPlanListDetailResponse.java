package com.example.asset_management_idnes.model.response;

import com.example.asset_management_idnes.domain.PlanList;
import com.example.asset_management_idnes.statics.PlanListDetailStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class ListPlanListDetailResponse {

    Long id;

    private String nameOfPlanList;

    Set<PlanList> planList ;    // Danh sách (năm nào)

    String approvedBy; // Người phê duyệt

    LocalDate createdDate; // Ngày tạo

    LocalDate approvedDate; // Ngày phê duyệt

    PlanListDetailStatus planListDetailStatus;  // Trạng thái kế hoạch
}
