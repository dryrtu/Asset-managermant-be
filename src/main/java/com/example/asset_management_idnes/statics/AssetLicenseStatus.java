package com.example.asset_management_idnes.statics;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AssetLicenseStatus {

    FOREVER("Vĩnh viễn"),
    LIMITED_TIME("Có thời hạn");

    public String name;

    @JsonValue
    public String toName() {
        return name;
    }
}
