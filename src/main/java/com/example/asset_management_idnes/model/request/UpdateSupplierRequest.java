package com.example.asset_management_idnes.model.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSupplierRequest {

   private String supplierName;  // Tên công ty
    private String address;  // Địa chỉ
    private   String contact;  // Liên hệ

    private String supplierCode;  // Mã công ty

}
