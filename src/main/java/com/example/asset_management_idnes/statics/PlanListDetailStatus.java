package com.example.asset_management_idnes.statics;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PlanListDetailStatus {

    DRAFT("Nháp"),
    PENDING("Chờ phê duyệt"),
    APPROVED("Đã phê duyệt"),
    CANCELLED("Từ chối phê duyệt");

    public String name;

    @JsonValue
    public String toName() {
        return name;
    }
}
