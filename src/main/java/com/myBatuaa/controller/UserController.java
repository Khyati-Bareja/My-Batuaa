package com.myBatuaa.controller;

import com.myBatuaa.model.Role;
import com.myBatuaa.repository.UserRepository;
import com.myBatuaa.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.myBatuaa.model.User;

@RestController
//@RequiredArgsConstructor
@RequestMapping("users/api/v1")
public class UserController {

    /*
 Register Account(User user)
Login(String email, String password)+
CreateWallet()
Logout
getAccountByUserId(String userId)
	 */
    @Autowired
    private UserService userService;


    @PostMapping("/register")
    //Register a new user and create wallet automatically
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user) {
        // call service once
        User savedUser = userService.registerUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }


    /*Login user with email, password, and role
    Using GET for local testing; in production, should use POST with @RequestBody for security.*/

    @GetMapping("/login")
    public ResponseEntity<?> loginUser(@RequestParam String emailId, @RequestParam String password, @RequestParam Role role) {

        // Call the service once and get the User
        User user = userService.login(emailId, password, role);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}



    /*//Logout
    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        return null;
    }

    @GetMapping("/user-by-userId/{userId}")
    //getAccountByUserId(String userId)
    public ResponseEntity<?> getuserByuserId(Integer userId) {
        return null;
    }
}*/

