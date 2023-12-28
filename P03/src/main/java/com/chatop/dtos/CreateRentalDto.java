package com.chatop.dtos;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

public class CreateRentalDto {

    private MultipartFile picture;
    private String name;
    private Integer surface;
    private Integer price;

    public MultipartFile getPicture() {
        return picture;
    }

    public void setPicture(MultipartFile picture) {
        this.picture = picture;
    }

    private String description;
    private Date createdAt;
    private Date updatedAt;



    public String getName() {
        return name;
    }

    public CreateRentalDto setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getSurface() {
        return surface;
    }

    public CreateRentalDto setSurface(Integer surface) {
        this.surface = surface;
        return this;
    }

    public Integer getPrice() {
        return price;
    }

    public CreateRentalDto setPrice(Integer price) {
        this.price = price;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CreateRentalDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public CreateRentalDto setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public CreateRentalDto setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}
