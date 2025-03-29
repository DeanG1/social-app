package com.social_app.social_app.service;

import com.social_app.social_app.models.User;
import com.social_app.social_app.repository.UserRepository;
import org.hibernate.id.IntegralDataTypeHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(User user) {
        User newUser = new User();
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setFollowers(user.getFollowers());
        newUser.setFollowings(user.getFollowings());
        newUser.setGender(user.getGender());
        newUser.setId(user.getId());
        User savedUser = userRepository.save(newUser);
        return savedUser;
    }

    @Override
    public User findUserById(Integer userId) throws Exception{
        User user = userRepository.findById(userId).orElse(null);
        if(user == null){
            throw new Exception("User with id " + userId + " not found");
        }
        return user;
    }

    @Override
    public User findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return user;
    }

    @Override
    public User followUser(Integer userId1, Integer userId2) throws Exception {
        User user1 = findUserById(userId1);
        User user2 = findUserById(userId2);
        user2.getFollowers().add(user1.getId());
        user1.getFollowings().add(user2.getId());
        userRepository.save(user1);
        userRepository.save(user2);
        return user1;
    }

    @Override
    public User updateUser(User user, Integer userId) throws Exception {
        User currentUser = userRepository.findById(userId).orElse(null);
        if(currentUser == null){
            throw new Exception("User with id " + userId + " not found");
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

    @Override
    public List<User> searchUser(String query) {
        return userRepository.searchUser(query);
    }
}
