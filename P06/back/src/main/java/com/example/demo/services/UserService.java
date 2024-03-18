package com.example.demo.services;

import com.example.demo.dtos.UserDto;
import com.example.demo.models.Post;
import com.example.demo.models.Topic;
import com.example.demo.models.User;
import com.example.demo.payloads.requests.LoginRequest;
import com.example.demo.payloads.requests.SignupRequest;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JWTService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final JWTService jwtService;
    private final TopicService topicService;

    public User findById(Long id) {
        return this.userRepository.findById(id).orElse(null);
    }

    public User findByEmail(String email) {
        return this.userRepository.findByEmail(email).orElse(null);
    }

    public User addUser(SignupRequest signUpRequest) {
        User user = new User(
                signUpRequest.getFirstName(),
                signUpRequest.getLastName(),
                signUpRequest.getEmail(),
                passwordEncoder.encode(signUpRequest.getPassword()),
                new ArrayList<>()
        );
        return userRepository.save(user);
    }

    public String update(User user, LoginRequest updateRequest) {
        boolean updateuser = false;

        if (updateRequest.getEmail() != null && updateRequest.getEmail().length() > 4) {
            user.setEmail(updateRequest.getEmail());
            updateuser = true;
        }
        if (updateRequest.getPassword() != null && updateRequest.getPassword().length() >= 4) {
            user.setPassword(passwordEncoder.encode(updateRequest.getPassword()));
            updateuser = true;
        }
        if (updateuser) {
            this.userRepository.save(user);
            return jwtService.generateToken(updateRequest);
        } else {
            LoginRequest loginRequest = new LoginRequest();
            loginRequest.setEmail(user.getEmail());
            return jwtService.generateToken(loginRequest);
        }
    }

    public Boolean authenticate(LoginRequest loginRequest) {
        Optional<User> user = userRepository.findByEmail(loginRequest.getEmail());
        if (user.isPresent()) {
            if (passwordEncoder.matches(loginRequest.getPassword(), user.get().getPassword()))
                return true;
        }
        return false;
    }

    public Topic subscribe(User user, Long topicId) {
        Topic topic = this.topicService.findById(topicId);
        List<Topic> userTopics = user.getTopics();
        userTopics.add(topic);
        user.setTopics(userTopics);
        this.userRepository.save(user);
        return topic;
    }

    public Topic unsubscribe(User user, Long topicId) {
        Topic topic = this.topicService.findById(topicId);
        List<Topic> userTopics = user.getTopics();
        userTopics.removeAll(Collections.singleton(topic));
        user.setTopics(userTopics);
        this.userRepository.save(user);
        return topic;
    }
}
