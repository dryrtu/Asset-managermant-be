package com.example.asset_management_idnes.model.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class ListVersionUpdateByShoppingPlanResponse {

    String version;

}
