package com.chatop.dtos;

public class UpdateRentalDto {

    private String name;
    private Integer surface;
    private Integer price;
    private String description;



    public String getName() {
        return name;
    }

    public UpdateRentalDto setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getSurface() {
        return surface;
    }

    public UpdateRentalDto setSurface(Integer surface) {
        this.surface = surface;
        return this;
    }

    public Integer getPrice() {
        return price;
    }

    public UpdateRentalDto setPrice(Integer price) {
        this.price = price;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public UpdateRentalDto setDescription(String description) {
        this.description = description;
        return this;
    }
}
