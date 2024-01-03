package com.example.asset_management_idnes.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_type_detail")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductTypeDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "type_detail")
    String typeDetail;

    @Column(name = "description")
    String description;  // Mô tả

    @ManyToMany
    @JoinTable(name = "productType_typeDetail",
            joinColumns = @JoinColumn(name = "product_type_id"),
            inverseJoinColumns = @JoinColumn(name = "product_type_detail_id"))
    Set<ProductType> productTypes;

}
