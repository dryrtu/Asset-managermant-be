package com.example.asset_management_idnes.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "unit")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "unit")
    String unitName;    // Tên đơn vị tính

    @Column(name = "description")
    String description;    // Mô tả

}
