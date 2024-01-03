package com.example.asset_management_idnes.model.request;

import com.example.asset_management_idnes.domain.PlanListDetail;
import com.example.asset_management_idnes.statics.PlanListDetailStatus;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchPlanListRequest {
    Long planListID;

    String nameOfPlanList;

    LocalDate createdDateStart;

    LocalDate createdDateEnd;

    LocalDate approvedDateStart;

    LocalDate approvedDateEnd;

    PlanListDetailStatus planListDetailStatus;

    Pageable pageable;
}

