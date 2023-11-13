package com.umpalumpy.weather.repository;

import com.umpalumpy.weather.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDao extends JpaRepository<User, Integer> {

    public User findById(int id);

    public User findOneByEmail(String email);

    public List<User> findAll();

	public User save(User user);

    public void deleteById(int id);

}
