package com.myBatuaa.service;

import com.myBatuaa.model.Role;
import com.myBatuaa.model.User;

public interface UserService {

    User registerUser(User user);
    User login(String emailId, String password, Role role);
}
