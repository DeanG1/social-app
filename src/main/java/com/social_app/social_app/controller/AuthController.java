package com.social_app.social_app.controller;

import com.social_app.social_app.config.JwtProvider;
import com.social_app.social_app.models.User;
import com.social_app.social_app.repository.UserRepository;
import com.social_app.social_app.request.LoginRequest;
import com.social_app.social_app.response.ApiResponse;
import com.social_app.social_app.response.AuthResponse;
import com.social_app.social_app.service.CustomerUserDetailsService;
import com.social_app.social_app.service.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.method.AuthorizeReturnObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> createUser(@RequestBody User user) throws Exception {

        User userExists = userRepository.findByEmail(user.getEmail());
        if(userExists != null){
            throw new Exception("email already used with another account");
        }

        User newUser = this.userService.registerUser(user);
//        newUser.setFirstName(user.getFirstName());
//        newUser.setLastName(user.getLastName());
//        newUser.setEmail(user.getEmail());
//        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
//        newUser.setGender(user.getGender());
        User savedUser = userRepository.save(newUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());
        return new ResponseEntity("User created successfully", HttpStatus.CREATED);
    }

    @PostMapping("/signIn")
    public AuthResponse signin(@RequestBody LoginRequest loginRequest)  {
        Authentication authentication = authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        String token = JwtProvider.generateToken(authentication);
        AuthResponse response = new AuthResponse(token, "Login successful!");

        return response;
    }

    private Authentication authenticate(String email, String password) {
        UserDetails userDetails = customerUserDetailsService.loadUserByUsername(email);

        if(userDetails == null){
            throw new BadCredentialsException("Invalid username!");
        }
        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("Invalid password!");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
