package com.example.asset_management_idnes.statics;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AssetStatus {

    ACTIVE("Active"),
    INACTIVE("Inactive");


    public String name;

    @JsonValue
    public String toName() {
        return name;
    }
}
