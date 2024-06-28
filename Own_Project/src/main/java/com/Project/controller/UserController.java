package com.Project.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Project.DTO.PasswordUpdateDTO;
import com.Project.entity.User;
import com.Project.exception.EmailAlreadyExistsException;
import com.Project.exception.InvalidPasswordException;
import com.Project.pojo.AuthRequest;
import com.Project.response.ErrorResponse;
import com.Project.response.JwtResponse;
import com.Project.service.UserService;
import com.Project.serviceImpl.JwtService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private JwtService jwtService;
	@Autowired
	private AuthenticationManager authenticationManager;
	
//RGISTRATION TO THE USER API   
	 	@PostMapping(value = "/registration", produces = MediaType.APPLICATION_JSON_VALUE)
	 	 public ResponseEntity<ErrorResponse> registerUser(@RequestBody  User user) throws EmailAlreadyExistsException {
	         userService.registerUser(user);
			 ErrorResponse userResponse = new ErrorResponse("User Registration SuccessFul!");
			 return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
	     }
//LOGIN API 
	   @PostMapping("/login")
	    public ResponseEntity<?> generateToken(@RequestBody AuthRequest authRequest) {
	        try {
	            String username = authRequest.getUsername();
	            String password = authRequest.getPassword();

	            // Check if username (email) or password is empty or null
	            if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
	                return ResponseEntity.badRequest().body(new ErrorResponse("Please enter your email and password!"));
	            }

	            // Attempt authentication
	            try {
	                Authentication authentication = authenticationManager.authenticate(
	                        new UsernamePasswordAuthenticationToken(username, password));

	                // Assuming authentication was successful, retrieve user details
	                Optional<User> optionalUser = userService.getUserByEmail(username);

	                if (optionalUser.isPresent()) {
	                    final Map<String, Object> token = jwtService.generateToken(username);
	                    // Include user data in the response
	                    return ResponseEntity.ok(new JwtResponse(token, "Login successful!", optionalUser.get()));
	                } else {
	                    // This branch may not be reached if authentication fails
	                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("Email does not exist"));
	                }
	            } catch (BadCredentialsException e) {
	                // Handle invalid credentials, can differentiate based on userService.getUserByEmail result
	                Optional<User> userExists = userService.getUserByEmail(username);
	                if (userExists.isPresent()) {
	                    // User exists but password is wrong
	                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("Invalid email or password"));
	                } else {
	                    // User does not exist
	                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("Email does not exist"));
	                }
	            }

	        } catch (Exception e) {
	            // Handle other exceptions (e.g., internal server error)
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("Internal server error"));
	        }
	    }    
//CHANGE PASSWORD API
	    @PutMapping("/update-password/{id}")
	    public ResponseEntity<ErrorResponse>updatePassword(@PathVariable Long id,@RequestBody PasswordUpdateDTO passwordUpdateDTO) throws InvalidPasswordException
	    {
	    	userService.updatePassword(id,passwordUpdateDTO);
	    	return ResponseEntity.ok(new ErrorResponse("Password update successfully"));
	    }

}
