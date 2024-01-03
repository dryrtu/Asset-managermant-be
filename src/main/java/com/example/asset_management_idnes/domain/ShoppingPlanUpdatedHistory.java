package com.example.asset_management_idnes.domain;

import com.example.asset_management_idnes.statics.AssetStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "shopping_plan_history")
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class ShoppingPlanUpdatedHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "shopping_plan_history_id")
    private ShoppingPlan shoppingPlan;

    @Column(name = "product_name_history")
    private String productName;

    @Column(name = "type_detail_history")
    private String typeDetail;

    @Column(name = "type_history")
    private String type;

    @Column(name = "unit_history")
    private String unit;

    @Column(name = "currency_history")
    private String currency;

    @Column(name = "quantity_history")
    private Integer quantity;

    @Column(name = "description_history")
    private String description;

    @Column(name = "plan_list_detail_history")
    private String planListDetail;

    @Column(name = "update_datetime")
    private LocalDate updateDate;

    @Column(name = "version")
    private String version;

    @Column(name = "modifiedBy")
    private String modifiedBy;

}
