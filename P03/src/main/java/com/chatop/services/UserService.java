package com.chatop.services;

import com.chatop.dtos.LoginUserDto;
import com.chatop.dtos.RegisterUserDto;
import com.chatop.dtos.UserDto;
import com.chatop.entities.User;
import com.chatop.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User addUser(RegisterUserDto input) {
        var user = new User();
        user.setUsername(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setEmail(input.getEmail());
        user.setRole("USER");

        return userRepository.save(user);
    }

    public Boolean authenticate(LoginUserDto loginUserDto) {
       Optional<User> user =  userRepository.findByEmail(loginUserDto.getEmail());
       if (user.isPresent()){
           if(passwordEncoder.matches(loginUserDto.getPassword(), user.get().getPassword()))
               return true;
       }
        return false;
    }

    public UserDto getUser(Integer userID){
        Optional<User> user = userRepository.findById(userID);
        UserDto response = new UserDto();
        if (user.isPresent()) {
            response.setId(user.get().getId());
            response.setName(user.get().getUsername());
            response.setEmail(user.get().getEmail());
            response.setCreated_at(user.get().getCreated_at());
            response.setUpdated_at(user.get().getUpdated_at());
        }
        return response;
    }
}
