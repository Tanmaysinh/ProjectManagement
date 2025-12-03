package com.example.pm.controller;

import com.example.pm.dto.AuthRequest;
import com.example.pm.dto.RegisterRequest;
import com.example.pm.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService){
        this.userService = userService;
    }
    public record TokenResponse(String token) {}


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {

        String token=userService.login(request.getUsername(),request.getPassword());
        if (token == null || token.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password");
        }
        return ResponseEntity.ok(new TokenResponse(token));
    }



    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req){
        userService.register(req.getUsername(), req.getPassword(), req.getEmail());
        return ResponseEntity.ok().build();
    }

}



