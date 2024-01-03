package com.example.asset_management_idnes.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "supplier")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "supplier_name")
    String supplierName;  // Tên công ty

    @Column(name = "contract_code")
    String supplierCode;  // Mã công ty

    @Column(name = "supplier_address")
    String address;  // Địa chỉ

    @Column(name = "supplier_contact")
    String contact;  // Liên hệ
}