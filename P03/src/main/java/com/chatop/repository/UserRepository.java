package com.chatop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.chatop.entities.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
	public User findByUsername(String username);

	Optional<User> findByEmail(String email);
}