package com.example.asset_management_idnes.domain;

import com.example.asset_management_idnes.statics.AssetStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "shopping_plan")
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class ShoppingPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Size(max = 128)
    @NotNull
    @Column(name = "serial")
    String serial;  // Serial

    @Size(max = 2028, message = "Product Name not blank")
    @NotNull
    @Column(name = "product_name")
    String productName;  //Tên sản phẩm

    @ManyToMany
    @JoinTable(name = "shoppingPlan_productTypeDetail",
            joinColumns = @JoinColumn(name = "shopping_plan_id"),
            inverseJoinColumns = @JoinColumn(name = "product_type_detail_id"))
    Set<ProductTypeDetail> productTypeDetails;

    @ManyToMany
    @JoinTable(name = "shoppingPlan_unit",
            joinColumns = @JoinColumn(name = "shopping_plan_id"),
            inverseJoinColumns = @JoinColumn(name = "unit_id"))
    Set<Unit> units;

    @ManyToMany
    @JoinTable(name = "shoppingPlan_currency",
            joinColumns = @JoinColumn(name = "shopping_plan_id"),
            inverseJoinColumns = @JoinColumn(name = "currency_id"))
    Set<Currency> currencies;

    @Column(name = "quantity")
    Integer quantity; // Số lượng

    @Column(name = "status")
    AssetStatus status;  // Trạng thái

    @Column(name = "description")
    String description; // Mô tả

    @ManyToMany
    @JoinTable(name = "shoppingPlan_planList",
            joinColumns = @JoinColumn(name = "shopping_plan_id"),
            inverseJoinColumns = @JoinColumn(name = "plan_list_detail_id"))
     Set<PlanListDetail> planListDetails;

    @Column(name = "isDraft")
    boolean isDraft; // Bản nháp

    @CreatedDate
    LocalDate createdDateTime;

    @CreatedBy
    String createdBy;

    @LastModifiedBy
    String modifiedBy;

    @Column(name = "approvedBy")
    String approvedBy; // Người phê duyệt

    @LastModifiedDate
    LocalDate modifiedDate;

    @Column(name = "expected_price")
    Integer expectedPrice;  // Đơn giá dự kiến



}
