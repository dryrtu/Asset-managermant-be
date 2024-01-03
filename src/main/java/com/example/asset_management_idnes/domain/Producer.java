//package com.example.asset_management_idnes.domain;
//
//import lombok.*;
//import lombok.experimental.FieldDefaults;
//
//import javax.persistence.*;
//import java.util.List;
//
//@Data
//@Entity
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//@Table(name = "producer")
//@FieldDefaults(level = AccessLevel.PRIVATE)
//
//public class Producer {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    Long id;
//
//    @Column(name = "producer_name")
//    String producerName;
//
//    @Column(name = "address")
//    String address;
//
//    @ElementCollection
//    @CollectionTable(
//            name = "contact",
//            joinColumns = @JoinColumn(name = "producer_id")
//    )
//    List<String> contacts;
//
//    @Column(name = "note")
//    String note;
//}
