package com.umpalumpy.weather.service;

import com.umpalumpy.weather.model.User;
import com.umpalumpy.weather.model.auth.AuthRequest;
import com.umpalumpy.weather.model.auth.AuthResponse;
import com.umpalumpy.weather.security.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userService.findOneByEmail(request.getEmail());
        var jwtToken = jwtService.generateToken(user);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(jwtToken);
        return authResponse;
    }

}
