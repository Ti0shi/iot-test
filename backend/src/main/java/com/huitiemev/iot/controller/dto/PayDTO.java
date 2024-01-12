package com.huitiemev.iot.controller.dto;

public class PayDTO {
    private Float amount;

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public PayDTO(Float amount) {
        this.amount = amount;
    }

    public PayDTO() {
    }
}
