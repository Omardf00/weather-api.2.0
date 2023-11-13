package com.umpalumpy.weather.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.umpalumpy.weather.model.Role;
import com.umpalumpy.weather.model.User;
import com.umpalumpy.weather.model.UserDetail;
import com.umpalumpy.weather.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/weather/v1/user")
public class UserController {

    final PasswordEncoder encoder;

    final
    UserService userService;


    public UserController(PasswordEncoder encoder, UserService userService) {
        this.encoder = encoder;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers(){

        Map<String, Object> response = new HashMap<>();
        List<User> users;

        try {
            users = userService.findAll();
        } catch (DataAccessException e){
            response.put("error", "We ran into a problem trying to access the database");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            response.put("error", "The service is not available at the moment");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@Valid @RequestBody User user, BindingResult result) {

        Map<String, Object> response = new HashMap<>();
        UserDetail userDetail = user.getUserDetail();
        userDetail.setUser(user);
        user.setUserDetail(userDetail);
        User newUser;

        if (result.hasErrors()) {
            for(FieldError error : result.getFieldErrors()) {
                response.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (userService.findOneByEmail(user.getEmail()) != null) {
            response.put("error", "This email is already used by another user. Try another one");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            //We will force the creation of reader users. For more privileges, an admin must change it
            Role role = new Role();
            role.setRoleId(1);
            role.setRoleName("Reader");
            user.setRole(role);

            //We encode the password before saving it
            String password = user.getPassword();
            user.setPassword(encoder.encode(password));
            newUser = userService.save(user);
        } catch (DataAccessException e){
            response.put("error", "We ran into a problem trying to access the database");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            response.put("error", "The service is not available at the moment");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "The user has been successfully created");
        response.put("user", newUser);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") int id){

        User user;
        Map<String, Object> response = new HashMap<>();

        try{
            user = userService.findById(id);

            if(user==null){
                response.put("message","There's no user with id " + id + " in the database");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

        } catch (DataAccessException e){
            response.put("error", "We ran into a problem trying to access the database");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            response.put("error", "The service is not available at the moment");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<?> getUserByEmail(@RequestParam String email){

        User user;
        Map<String, Object> response = new HashMap<>();

        try{
            user = userService.findOneByEmail(email);

            if(user==null){
                response.put("message","There's no user with the email " + email + " in the database");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

        } catch (DataAccessException e){
            response.put("error", "We ran into a problem trying to access the database");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            response.put("error", "The service is not available at the moment");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@Valid @RequestBody User user, BindingResult result) {

        UserDetail userDetail = user.getUserDetail();
        userDetail.setUser(user);
        user.setUserDetail(userDetail);
        User updatedUser;
        Role role;
        Map<String, Object> response = new HashMap<>();

        try{

            if (result.hasErrors()) {
                for(FieldError error : result.getFieldErrors()){
                    response.put(error.getField(), error.getDefaultMessage());
                }
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            //We compare the password to see if it matches before updating
            String encodedPasswordDataBase = userService.findById(user.getId()).getPassword();
            if (! encoder.matches(user.getPassword(), encodedPasswordDataBase)) {
                response.put("error", "The password does not match");
                System.out.println(encodedPasswordDataBase);
                System.out.println(user.getPassword());
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            //We keep the same role that we had before updating, just in case a user tries to update it
            role = userService.findOneByEmail(user.getEmail()).getRole();
            user.setRole(role);

            //We encode the password
            String password = user.getPassword();
            user.setPassword(encoder.encode(password));

            updatedUser = userService.save(user);

        } catch (DataAccessException e){
            response.put("error", "We ran into a problem trying to access the database");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            response.put("error", "The service is not available at the moment");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "The user has been successfully updated");
        response.put("updatedUser", updatedUser);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") int id){

        Map<String, Object> response = new HashMap<>();

        try {

            if(userService.findById(id)==null) {
                response.put("error", "There's no user with the id " + id);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            userService.deleteById(id);
            response.put("message", "The user has been successfully deleted");
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (DataAccessException e){
            response.put("error", "We ran into a problem trying to access the database");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            response.put("error", "The service is not available at the moment");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
