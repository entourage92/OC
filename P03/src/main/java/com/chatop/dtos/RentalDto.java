package com.chatop.dtos;

import java.util.Date;

public class RentalDto {

    private String picture;
    private String name;
    private Integer surface;
    private Integer price;
    private String description;
    private Date createdAt;
    private Date updatedAt;
    private Integer owner_id;
    private Integer id;

    public String getPicture() {
        return picture;
    }

    public RentalDto setPicture(String picture) {
        this.picture = picture;
        return this;
    }

    public String getName() {
        return name;
    }

    public RentalDto setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getSurface() {
        return surface;
    }

    public RentalDto setSurface(Integer surface) {
        this.surface = surface;
        return this;
    }

    public Integer getPrice() {
        return price;
    }

    public RentalDto setPrice(Integer price) {
        this.price = price;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public RentalDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public RentalDto setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public RentalDto setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public Integer getOwner_id() {
        return owner_id;
    }

    public RentalDto setOwner_id(Integer userIDt) {
        this.owner_id = userIDt;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
