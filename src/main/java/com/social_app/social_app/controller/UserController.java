package com. social_app. social_app. controller;

import com.social_app.social_app.models.User;
import com.social_app.social_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public List<User> getAllUsers(){
        List<User> users = userRepository.findAll();
        return  users;
    }

    @GetMapping("/users/{userId}")
    public User getUser(@PathVariable ("userId") Integer id) throws Exception{
        User user = userRepository.findById(id).orElse(null);
        if(user == null){
            throw new Exception("User with id " + id + " not found");
        }
        return user;
    }
    @PostMapping("/users")
    public User createUser(@RequestBody User user){
        User newUser = new User();
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        newUser.setId(user.getId());
        return userRepository.save(newUser);
    }
    @PutMapping("/users/{userId}")
    public User updateUser(@RequestBody User user, @PathVariable ("userId") Integer id) throws Exception{
        User currentUser = userRepository.findById(id).orElse(null);
        if(currentUser == null){
            throw new Exception("User with id " + id + " not found");
        }
        User previousUser = currentUser;

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
