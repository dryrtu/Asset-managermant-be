package com.example.asset_management_idnes.domain;


import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "refresh_token")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RefreshToken extends BaseEntity {

    @JoinColumn(name = "user_id")
    @ManyToOne(targetEntity = User.class)
    User user;

    @Column(name = "refresh_token")
    String refreshToken;

    //    @Type(type= "org.hibernate.type.NumericBooleanType")
    @Column(columnDefinition = "number(1,0) default 0")
    boolean invalidated;

}
