package com.example.asset_management_idnes.model.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductTypeDetailRequest {

    private String typeDetail;

    Set<Long> productTypeId;

    String description;  // Mô tả

}
