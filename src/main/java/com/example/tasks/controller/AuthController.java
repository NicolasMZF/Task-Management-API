package com.example.tasks.controller;

import com.example.tasks.dto.AuthenticationRequest;
import com.example.tasks.dto.AuthenticationResponse;
import com.example.tasks.entity.User;
import com.example.tasks.security.JwtUtil;
import com.example.tasks.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequest request){
        // 1. Check if user already exists
        if (userService.existsByEmail(request.email())){
            throw new RuntimeException("Email already in use");
        }

        // 2. Create the new user and encode the password
        User newUser = new User();
        newUser.setName("New User"); // Hardcoded for now
        newUser.setEmail(request.email());
        newUser.setPassword(passwordEncoder.encode(request.password()));

        userService.saveUser(newUser);

        //3. Generate the token
        UserDetails userDetails = userDetailsService.loadUserByUsername(newUser.getEmail());
        String jwtToken = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwtToken));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request){
        // 1. Authenticate the user
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    request.email(),
                    request.password()
            )
        );

        // 2. If authentication is correct, load the user and generate token
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.email());
        String jwtToken = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwtToken));
    }
}


