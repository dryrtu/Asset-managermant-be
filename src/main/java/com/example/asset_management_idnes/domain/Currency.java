package com.example.asset_management_idnes.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "currency")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "currency")
    String currencyUnit;  // Đơn vị tiền tệ

    @Column(name = "description")
    String description;  // Mô tả
}
