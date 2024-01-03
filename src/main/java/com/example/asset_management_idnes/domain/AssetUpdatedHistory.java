package com.example.asset_management_idnes.domain;

import com.example.asset_management_idnes.statics.AssetLicenseStatus;
import com.example.asset_management_idnes.statics.ContractStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "asset_history")
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class AssetUpdatedHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "asset_history_id")
    private Asset asset;

    private String itemCodeKt;  // Mã tài sản (kế toán nhập)

    @Column(name = "asset_history_name")
    String assetName;  // Tên tài sản


    @Column(name = "asset_type_detail_history")
    private String typeDetail;


    @Column(name = "asset_type_history")
    private String type;

    @Column(name = "asset_history_specifications")

    private String specifications;  // thông số kĩ thuật

    @Column(name = "asset_history_origin")

    private String productOrigin;   // Xuất xứ

    @Column(name = "asset_history_serial")

    private String serial;      // Serial

    @Column(name = "asset_history_description")

    private String description;  // mô tả thêm

    @Column(name = "asset_history_unit")

    private String unit; // đơn vị tính

    @Column(name = "asset_history_quantity")

    private Integer quantity; // Số lượng

    @Column(name = "asset_history_price")

    private Integer price;  // Đơn giá

    @Column(name = "asset_history_asset_license_status")

    private String assetLicenceStatus;  // Phân loại license

    @Column(name = "asset_history_licence_duration")

    private LocalDate licenceDuration;  // Thời hạn license

    @Column(name = "asset_history_day_started_using")

    private LocalDate dayStartedUsing; // Ngày bắt đầu sử dụng

    // Sửa thông tin hợp đồng

    @Column(name = "asset_history_contract_status")

    private String contractStatus;    // trạng thái hợp đồng

    @Column(name = "asset_history_contract_code")

    private String contractCode;    // Mã hợp đồng

    @Column(name = "asset_history_contract_name")

    private String contractName;    // Tên hợp đồng

    @Column(name = "asset_history_archive_link")

    private String archiveLink;    // Link SVN lưu trữ hợp đồng

    @Column(name = "asset_history_contract_start_date")

    private LocalDate contractStartDate;    // ngày bắt đầu hợp đồng

    @Column(name = "asset_history_contract_end_date")

    private LocalDate contractEndDate;    // ngày kết thúc hợp đồng

    @Column(name = "asset_history_warranty_period")

    private Integer warrantyPeriod;    // thời gian bảo hành

    @Column(name = "asset_history_warranty_start_date")

    private LocalDate warrantyStartDate;    // ngày bắt đầu bảo hành

    @Column(name = "asset_history_warranty_end_date")

    private LocalDate warrantyEndDate;    // ngày kết thúc bảo hành

    @Column(name = "asset_history_attached_files")

    private String attachedFiles;   // File đính kèm

    // End: Lưu thông tin hợp đồng

    @Column(name = "asset_history_lookingUpInformation")

    private String lookingUpInformation; // Thông tin bảo hành, bảo trì từ hãng sản xuất

    @Column(name = "asset_history_warranty_date")

    private LocalDate warrantyDate;     // Ngày bảo hành/bảo trì

    @Column(name = "asset_history_content")

    private String content;     // Nội dung


    @Column(name = "version")
    private String version;

    @Column(name = "modifiedBy")
    private String modifiedBy;
}
