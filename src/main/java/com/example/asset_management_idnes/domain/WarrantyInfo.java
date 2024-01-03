package com.example.asset_management_idnes.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "warranty_info")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WarrantyInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "warranty_date")
    LocalDate warrantyDate;

    @Column(name = "description")
    String description;

    @ManyToMany
    @JoinTable(name = "warranty_contract",
            joinColumns = @JoinColumn(name = "warranty_id"),
            inverseJoinColumns = @JoinColumn(name = "contract_id"))
    Set<Contract> contracts;    // Thông tin hợp đồng
}
