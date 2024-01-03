package com.example.asset_management_idnes.model.request;

import com.example.asset_management_idnes.statics.AssetStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class CreateShoppingPlanRequest {

    String serial;  // Serial

    String productName;  //Tên sản phẩm

    Set<Long> productTypeDetailId;

    Set<Long> unitId;

    Set<Long> currencyId;

    Integer quantity; // Số lượng

    AssetStatus status;  // Trạng thái

    String description; // Mô tả

    Set<Long> planListDetailId;    // Danh sách (năm nào)

    String createBy;

    LocalDate createdDateTime;

    String approvedBy; // Người phê duyệt

    boolean isDraft; // Bản nháp

    Integer expectedPrice;
}
