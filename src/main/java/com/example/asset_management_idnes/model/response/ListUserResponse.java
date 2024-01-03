package com.example.asset_management_idnes.model.response;

import com.example.asset_management_idnes.domain.BaseEntity;
import com.example.asset_management_idnes.domain.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class ListUserResponse extends BaseEntity {
    Long id;
    String name;

    String email;

     Set<Role> roles;


}

