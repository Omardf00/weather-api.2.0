package com.umpalumpy.weather.service;

import com.umpalumpy.weather.model.User;

import java.util.List;

public interface UserService {

    public User findById(int id);

    public User findOneByEmail(String email);

    public List<User> findAll();

    public User save(User user);

    public void deleteById(int id);

}
