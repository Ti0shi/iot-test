package com.huitiemev.iot.entity;

import java.util.Date;

import jakarta.persistence.*;

@Entity(name = "thiefs")
public class Thief {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private Date createdAt;
    private Date interceptedAt;

    // relations
    private String caddieId;

    // getters
    public String getCaddieId() {
        return caddieId;
    }

    public String getId() {
        return id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getInterceptedAt() {
        return interceptedAt;
    }

    // setters
    public void setCaddieId(String caddieId) {
        this.caddieId = caddieId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setInterceptedAt(Date interceptedAt) {
        this.interceptedAt = interceptedAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

}
