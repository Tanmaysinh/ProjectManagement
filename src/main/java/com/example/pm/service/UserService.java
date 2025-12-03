package com.example.pm.service;

import com.example.pm.config.JwtService;
import com.example.pm.entity.User;
import com.example.pm.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public void register(String username, String password, String email){
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("username already taken");
        }
        User u = new User();
        u.setUsername(username);
        u.setPassword(passwordEncoder.encode(password));
        u.setEmail(email);
        userRepository.save(u);
    }

    public String login(String username, String password){
        Optional<User> userData=userRepository.findByUsername(username);
        if (userData.isPresent()) {
            User user=userData.get();
            if(passwordEncoder.matches(password,user.getPassword())){
                return jwtService.generateToken(user.getId());
            }

        }
        return null;
    }
}
