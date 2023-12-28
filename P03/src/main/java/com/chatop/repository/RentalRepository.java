package com.chatop.repository;

import com.chatop.entities.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Integer> {

    public Rental findByPrice(Integer price);
    public Rental findByName(String name);

}