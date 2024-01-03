package com.example.asset_management_idnes.model.response;

import com.example.asset_management_idnes.domain.Currency;
import com.example.asset_management_idnes.domain.PlanListDetail;
import com.example.asset_management_idnes.domain.ProductTypeDetail;
import com.example.asset_management_idnes.domain.Unit;
import com.example.asset_management_idnes.statics.AssetStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class ListShoppingPlanResponse {

    Long id;

    String serial;  // Serial

    String productName;  //Tên sản phẩm

    Set<ProductTypeDetail> productTypeDetails;

    Set<Unit> units;

    Set<Currency> currencies;

    Integer quantity; // Số lượng

    AssetStatus status;  // Trạng thái

    String description; // Mô tả

    Set<PlanListDetail> planListDetails;    // Danh sách (năm nào)

    boolean isDraft;

    String createdBy;

    LocalDate createdDateTime;

    Integer expectedPrice;

}
