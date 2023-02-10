package com.example.demosecurityjwt.controller.interfaces;


import com.example.demosecurityjwt.model.User;

import java.util.List;

public interface UserController {

    List<User> getUsers();
    void saveUser(User user);

}
