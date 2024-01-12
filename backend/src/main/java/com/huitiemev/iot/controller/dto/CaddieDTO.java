package com.huitiemev.iot.controller.dto;

import com.huitiemev.iot.entity.Caddie;
import com.huitiemev.iot.entity.enums.CaddieState;

public class CaddieDTO {
    private String id;

    private String label;

    private String state;

    private String userId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String user_id) {
        this.userId = user_id;
    }

    public CaddieDTO(Caddie caddie) {
        this.id = caddie.getId();
        this.label = caddie.getLabel();
        this.state = caddie.getState().toString();
        this.userId = null;
    }

    public Caddie toEntity() {
        Caddie caddie = new Caddie();
        caddie.setId(this.id);
        caddie.setLabel(this.label);
        caddie.setState(CaddieState.valueOf(this.state));
        return caddie;
    }

    public CaddieDTO() {
    }
}