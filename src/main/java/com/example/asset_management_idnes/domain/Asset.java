package com.example.asset_management_idnes.domain;

import com.example.asset_management_idnes.statics.AssetLicenseStatus;
import com.example.asset_management_idnes.statics.AssetStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
@Builder(builderMethodName = "assetBuilder")
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "asset")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "item_code")
    String itemCode;  // Mã tài sản (tự động generate)

    @Column(name = "asset_name")
    String assetName;  // Tên tài sản

    @Column(name = "item_code_kt")
    String itemCodeKt;  // Mã tài sản (kế toán nhập)

    @OneToOne
    @JoinColumn(name = "shopping_plan_id")
    ShoppingPlan shoppingPlan; // Sản phẩm

    @Size(max = 500)
//    @NotNull
    @Column(name = "serial")
    String serial;  // Serial

    @Column(name = "specifications")
    String specifications;  // thông số kĩ thuật

    @Column(name = "description")
    String description;  // mô tả thêm

    @Column(name = "quantity")
    Integer quantity; // Số lượng

    @Column(name = "price")
    Integer price; // Đơn giá

    @Column(name = "day_started_using")
    LocalDate dayStartedUsing; // Ngày bắt đầu sử dụng

    @ManyToMany
    @JoinTable(name = "asset_supplier",
            joinColumns = @JoinColumn(name = "shopping_plan_id"),
            inverseJoinColumns = @JoinColumn(name = "supplier_id"))
    Set<Supplier> suppliers;    // Nhà cung cấp

    @Column(name = "origin")
    String productOrigin;   // Xuất xứ

    @Column(name = "asset_status")
    AssetStatus assetStatus;   // trạng thái


    @Column(name = "asset_license_status")
    AssetLicenseStatus assetLicenceStatus;  // Phân loại license

    @Column(name = "licence_duration")
    LocalDate licenceDuration;  // Thời hạn license

    @ManyToMany
    @JoinTable(name = "asset_unit",
            joinColumns = @JoinColumn(name = "asset_id"),
            inverseJoinColumns = @JoinColumn(name = "unit_id"))
    Set<Unit> units;

    @ManyToMany
    @JoinTable(name = "asset_manufacturer",
            joinColumns = @JoinColumn(name = "asset_id"),
            inverseJoinColumns = @JoinColumn(name = "manufacturer_id"))
    Set<Manufacturer> manufacturers;    // Hãng sản xuất

    @ManyToMany
    @JoinTable(name = "asset_warrantyInfo",
            joinColumns = @JoinColumn(name = "asset_id"),
            inverseJoinColumns = @JoinColumn(name = "warranty_id"))
    Set<WarrantyInfo> warrantyInfos;    // Thông tin bảo hành, bảo trì

    @ManyToMany
    @JoinTable(name = "asset_contract",
            joinColumns = @JoinColumn(name = "asset_id"),
            inverseJoinColumns = @JoinColumn(name = "contract_id"))
    Set<Contract> contracts;    // Thông tin hợp đồng

    @Column(name = "modifiedBy")
    private String modifiedBy;
}
