package com.example.asset_management_idnes.model.response;

import com.example.asset_management_idnes.domain.PlanList;
import com.example.asset_management_idnes.domain.ProductType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class ListProductTypeDetailResponse {

    Long id;

    private String typeDetail;

    Set<ProductType> productTypeId ;    // Danh sách (năm nào)

}
