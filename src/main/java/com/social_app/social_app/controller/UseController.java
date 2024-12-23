package com.social_app.social_app.controller;

import com.social_app.social_app.models.User;
import com.social_app.social_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class UseController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/users")
    public List<User> getUsers(){
        List<User> users = userRepository.findAll();
        return users;
    }
    @GetMapping("/users/{userId}")
    public User getUserById(@PathVariable("userId") Integer id) throws Exception{
        User user = userRepository.findById(id).orElse(null);
        if(user != null){
            return user;
        }
        throw new Exception("User with id " + id + " not found");
    }


    @PostMapping("/users")
    public User createUser(@RequestBody User user){
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setLastName(user.getLastName());
        newUser.setFirstName(user.getFirstName());
        newUser.setPassword(user.getPassword());
        newUser.setId(user.getId());

        User savedUser = userRepository.save(newUser);

        return savedUser;
    }
    @PutMapping("/users/{userId}")
    public User updateUser(@RequestBody User user, @PathVariable Integer userId) throws Exception{
        User user1 = userRepository.findById(userId).orElse(null);
        if(user1 == null){
            throw new Exception("User does not exist with id: " + userId);
        }
        User previousUser = user1;
        if(user.getFirstName() != null){
            previousUser.setFirstName(user.getFirstName());
        }
        if(user.getLastName() != null){
            previousUser.setLastName(user.getLastName());
        }
        if(user.getEmail() != null){
            previousUser.setEmail(user.getEmail());
        }
        User updatedUser = userRepository.save(previousUser);
        return updatedUser;
    }
    @DeleteMapping("users/{userId}")
    public String deleteUser(@PathVariable ("userId") Integer userId) throws Exception{
        User user = userRepository.findById(userId).orElse(null);
        if(user == null){
            throw new Exception("User with id " + userId + " not found");
        }
        userRepository.delete(user);
        return "User with id " + userId + " deleted successfully!";
    }
}
