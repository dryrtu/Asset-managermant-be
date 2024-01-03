package com.example.asset_management_idnes.model.request;

import com.example.asset_management_idnes.statics.AssetLicenseStatus;
import com.example.asset_management_idnes.statics.AssetStatus;
import com.example.asset_management_idnes.statics.ContractStatus;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateAssetRequest {


    String itemCodeKt;  // Mã tài sản (kế toán nhập)

    Long shoppingPlanId;  // Sản phẩm trong kế hoạch mua sắm

    String assetName;  // Tên tài sản

    Set<Long> productTypeDetailId;      // Nhóm tài sản

    String specifications;  // thông số kĩ thuật

    String productOrigin;   // Xuất xứ

    String serial;      // Serial
    String description;  // mô tả thêm
    Set<Long> unitIds;  //Đơn vị tính

    Integer quantity; // Số lượng

    Integer price;  // Đơn giá
    AssetLicenseStatus assetLicenceStatus;  // Phân loại license

    LocalDate licenceDuration;  // Thời hạn license

    LocalDate dayStartedUsing; // Ngày bắt đầu sử dụng

    // Sửa thông tin hợp đồng
    ContractStatus contractStatus;    // trạng thái hợp đồng

    String contractCode;    // Mã hợp đồng

    String contractName;    // Tên hợp đồng

    String archiveLink;    // Link SVN lưu trữ hợp đồng

    LocalDate contractStartDate;    // ngày bắt đầu hợp đồng

    LocalDate contractEndDate;    // ngày kết thúc hợp đồng

    Integer warrantyPeriod;    // thời gian bảo hành

    LocalDate warrantyStartDate;    // ngày bắt đầu bảo hành

    LocalDate warrantyEndDate;    // ngày kết thúc bảo hành

    Set<Long> supplierIds;    // Nhà cung cấp

    String attachedFiles;   // File đính kèm

    // End: Lưu thông tin hợp đồng

    Set<Long> contractIds;

    Set<Long> manufacturerIds;    // Hãng sản xuất

    String lookingUpInformation; // Thông tin bảo hành, bảo trì từ hãng sản xuất

    LocalDate warrantyDate;     // Ngày bảo hành/bảo trì

    String content;     // Nội dung

    Set<Long> warrantyInfoIds;    // Thông tin bảo hành, bảo trì

}
