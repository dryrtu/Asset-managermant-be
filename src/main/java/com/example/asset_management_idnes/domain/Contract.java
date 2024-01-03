package com.example.asset_management_idnes.domain;

import com.example.asset_management_idnes.statics.ContractStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "contract")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "contract_code")
    String contractCode;    // Mã hợp đồng

    @Column(name = "contract_name")
    String contractName;    // Tên hợp đồng

    @Column(name = "archive_link")
    String archiveLink;    // Link SVN lưu trữ hợp đồng

    @Column(name = "contract_start_date")
    LocalDate contractStartDate;    // ngày bắt đầu hợp đồng

    @Column(name = "contract_end_date")
    LocalDate contractEndDate;    // ngày kết thúc hợp đồng

    @Column(name = "warranty_period")
    Integer warrantyPeriod;    // thời gian bảo hành

    @Column(name = "warranty_start_date")
    LocalDate warrantyStartDate;    // ngày bắt đầu bảo hành

    @Column(name = "warranty_end_date")
    LocalDate warrantyEndDate;    // ngày kết thúc bảo hành

    @ManyToMany
    @JoinTable(name = "asset_supplier",
            joinColumns = @JoinColumn(name = "shopping_plan_id"),
            inverseJoinColumns = @JoinColumn(name = "supplier_id"))
    Set<Supplier> suppliers;    // Nhà cung cấp

    @Column(name = "attached_files")
    String attachedFiles;   // File đính kèm

    @Column(name = "contract_status")
    ContractStatus contractStatus;    // trạng thái hợp đồng

    @Column(name = "lookingUpInformation")
    String lookingUpInformation;   // Hướng dẫn tra cứu thông tin bảo hành, bảo trì
}
