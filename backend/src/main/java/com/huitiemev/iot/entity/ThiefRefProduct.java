package com.huitiemev.iot.entity;

import jakarta.persistence.*;

@Entity(name = "thiefRefProducts")
public class ThiefRefProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    // relations
    private String refProductId;
    private String thiefId;

    // getters
    public String getId() {
        return id;
    }

    public String getRefProductId() {
        return refProductId;
    }

    public String getThiefId() {
        return thiefId;
    }

    // setters

    public void setId(String id) {
        this.id = id;
    }

    public void setRefProductId(String refProductId) {
        this.refProductId = refProductId;
    }

    public void setThiefId(String thiefId) {
        this.thiefId = thiefId;
    }

}
