package com.chatop.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Table(name = "rental")
@Entity
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer surface;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false, columnDefinition = "text")
    private String description;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(nullable = false)
    private String picture;

    public String getPicture() {
        return picture;
    }

    public Rental setPicture(String picture) {
        this.picture = picture;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public Rental setId(Integer id) {
        this.id = id;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Rental setUser(User user) {
        this.user = user;
        return this;
    }

    public String getName() {
        return name;
    }

    public Rental setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getSurface() {
        return surface;
    }

    public Rental setSurface(Integer surface) {
        this.surface = surface;
        return this;
    }

    public Integer getPrice() {
        return price;
    }

    public Rental setPrice(Integer price) {
        this.price = price;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Rental setDescription(String description) {
        this.description = description;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Rental setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public Rental setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}