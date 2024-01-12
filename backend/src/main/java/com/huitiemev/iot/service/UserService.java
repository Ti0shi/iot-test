package com.huitiemev.iot.service;

import com.huitiemev.iot.entity.User;

import java.util.List;

public interface UserService {

    void createUser(User user);

    User getUserById(String id);

    User getUserByEmail(String email);

    User getUserByPhoneNumber(String phoneNumber);

    User getUserByEmailAndPassword(String email, String password);

    List<User> getAllUsers();

    // TODO NEED TO REMOVE
    void updateUser(User user);
}
