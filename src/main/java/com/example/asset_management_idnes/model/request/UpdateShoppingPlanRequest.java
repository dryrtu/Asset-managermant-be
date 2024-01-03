package com.example.asset_management_idnes.model.request;

import com.example.asset_management_idnes.statics.AssetStatus;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateShoppingPlanRequest {

    Long shoppingPlanId;

    String productName;  //Tên sản phẩm

    Set<Long> productTypeDetailId;

    Set<Long> unitId;

    Set<Long> currencyId;

    Integer quantity; // Số lượng

    boolean isDraft;  // Trạng thái

    String description; // Mô tả

    LocalDate modifiedDate;

    String modifiedBy;

    Integer expectedPrice;

}
