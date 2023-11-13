package com.umpalumpy.weather.controller;

import com.umpalumpy.weather.model.auth.AuthRequest;
import com.umpalumpy.weather.model.auth.AuthResponse;
import com.umpalumpy.weather.service.AuthService;
import com.umpalumpy.weather.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/weather/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest authRequest, BindingResult result) {

        Map<String, Object> response = new HashMap<>();

        if(result.hasErrors()) {
            for(FieldError error : result.getFieldErrors()) {
                response.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(authService.login(authRequest), HttpStatus.OK);
    }

}
