package com.example.lab4back.security.services;

import com.example.lab4back.security.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveUser(User user);
    User getUser(String username);
    List<User> getUsers();
}
