package com. social_app. social_app. controller;

import com.social_app.social_app.models.User;
import com.social_app.social_app.repository.UserRepository;
import com.social_app.social_app.service.UserService;
import com.social_app.social_app.service.UserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @GetMapping("/api/users")
    public List<User> getAllUsers(){
        List<User> users = userRepository.findAll();
        return  users;
    }

    @GetMapping("/api/users/{userId}")
    public User getUserById(@PathVariable ("userId") Integer id) throws Exception{
        User user = userService.findUserById(id);
        return user;
    }
    @PostMapping("/users")
    public User createUser(@RequestBody User user){
         User savedUser = userService.registerUser(user);
         return savedUser;
    }
    @PutMapping("/api/users/{userId}")
    public User updateUser(@RequestBody User user, @PathVariable ("userId") Integer id) throws Exception{
        User updatedUser = userService.updateUser(user, id);
        return updatedUser;
    }

    @PutMapping("/api/users/follow/{userId1}/{userId2}")
    public User followUser(@PathVariable Integer userId1, @PathVariable Integer userId2) throws Exception{
        User user = userService.followUser(userId1, userId2);
        return user;
    }
    @GetMapping("/api/users/search")
    public List<User> searchUser(@RequestParam("query") String query){
        List<User> users = userService.searchUser(query);
        return users;
    }
    @DeleteMapping("/users/{userId}")
    public String deleteUser(@PathVariable ("userId") Integer id) throws Exception{
        User user = userRepository.findById(id).orElse(null);
        if(user == null){
            throw new Exception("User with id " + id + " not found");
        }
        userRepository.delete(user);
        return "User with id " + id + " deleted successfully!";
    }
}
