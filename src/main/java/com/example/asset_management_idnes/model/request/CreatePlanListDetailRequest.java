package com.example.asset_management_idnes.model.request;

import com.example.asset_management_idnes.statics.PlanListDetailStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import java.time.LocalDate;
import java.util.Set;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class CreatePlanListDetailRequest {

    private String nameOfPlanList;

    Set<Long> planListId;    // Danh sách (năm nào)

    PlanListDetailStatus planListDetailStatus;  // Trạng thái kế hoạch

    String approvedBy; // Người phê duyệt

    LocalDate createdDate; // Ngày tạo

    LocalDate approvedDate; // Ngày phê duyệt

}
