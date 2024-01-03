package com.example.asset_management_idnes.model.request;

import com.example.asset_management_idnes.statics.ContractStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import java.time.LocalDate;
import java.util.Set;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class CreateContractRequest {

    String contractCode;    // Mã hợp đồng

    String contractName;    // Tên hợp đồng

    String archiveLink;    // Link SVN lưu trữ hợp đồng

    LocalDate contractStartDate;    // ngày bắt đầu hợp đồng

    LocalDate contractEndDate;    // ngày kết thúc hợp đồng

    LocalDate warrantyPeriod;    // thời gian bảo hành

    LocalDate warrantyStartDate;    // ngày bắt đầu bảo hành

    LocalDate warrantyEndDate;    // ngày kết thúc bảo hành

    Set<Long> supplierId;    // Nhà cung cấp

    String attachedFiles;   // File đính kèm

    ContractStatus contractStatus;    // trạng thái hợp đồng

}
