package com.huitiemev.iot.controller.dto;

import java.util.List;

public class GateDTO {
    private List<String> refProductIds;

    public List<String> getrefProductIds() {
        return refProductIds;
    }

    public void setrefProductIds(List<String> refProductIds) {
        this.refProductIds = refProductIds;
    }

    public GateDTO(List<String> refProductIds) {
        this.refProductIds = refProductIds;
    }

    public GateDTO() {
    }
}
