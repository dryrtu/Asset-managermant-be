package com.example.asset_management_idnes.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "manufacturer")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Manufacturer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "manufacturer")
    String manufacturer;

    @Column(name = "address")
    String address;

    @ElementCollection
    @CollectionTable(name = "contact", joinColumns = @JoinColumn(name = "manufacturer_id"))
    @Column(name = "contact")
    private List<Long> contacts;

    @Column(name = "note")
    String note;
}
