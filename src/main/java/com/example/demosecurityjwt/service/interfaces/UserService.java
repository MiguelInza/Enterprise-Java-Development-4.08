package com.example.demosecurityjwt.service.interfaces;

import com.example.demosecurityjwt.model.User;

import java.util.List;

public interface UserService {

    User saveUser(User user);
    List<User> getUsers();

}
