package com.example.asset_management_idnes.model.request;

import com.example.asset_management_idnes.domain.ShoppingPlan;
import com.example.asset_management_idnes.domain.Supplier;
import com.example.asset_management_idnes.statics.AssetLicenseStatus;
import com.example.asset_management_idnes.statics.AssetStatus;
import com.example.asset_management_idnes.statics.ContractStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class CreateAssetRequest {

    String itemCode;  // Mã tài sản

    String itemCodeKt;  // Mã tài sản (kế toán nhập)

    String serial;      // Serial

    Long shoppingPlanId;  // Sản phẩm trong kế hoạch mua sắm

    String assetName;  // Tên tài sản

    Integer quantity; // Số lượng

    Integer price;  // Đơn giá

    String productOrigin;   // Xuất xứ

    String specifications;  // thông số kĩ thuật

    String description;  // mô tả thêm

    AssetStatus assetStatus; // trạng thái

    AssetLicenseStatus assetLicenceStatus;  // Phân loại license

    LocalDate licenceDuration;  // Thời hạn license

    LocalDate dayStartedUsing; // Ngày bắt đầu sử dụng

    // Lưu thông tin hợp đồng
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

    ContractStatus contractStatus;    // trạng thái hợp đồng
    // End: Lưu thông tin hợp đồng

    Set<Long> contractIds;

    Set<Long> unitIds;

    Set<Long> manufacturerIds;    // Hãng sản xuất

    String lookingUpInformation; // Thông tin bảo hành, bảo trì từ hãng sản xuất

    LocalDate warrantyDate;     // Ngày bảo hành/bảo trì

    String content;     // Nội dung

    Set<Long> warrantyInfoIds;    // Thông tin bảo hành, bảo trì



}
