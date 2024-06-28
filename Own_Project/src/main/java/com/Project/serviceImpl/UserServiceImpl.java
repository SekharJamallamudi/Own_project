package com.Project.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Project.DTO.PasswordUpdateDTO;
import com.Project.entity.User;
import com.Project.exception.EmailAlreadyExistsException;
import com.Project.exception.InvalidPasswordException;
import com.Project.exception.UserNotFoundException;
import com.Project.repository.UserRepository;
import com.Project.service.UserService;
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	public User registerUser(User user) throws EmailAlreadyExistsException {
	    if (userRepository.existsByEmail(user.getEmail())) {
	        throw new EmailAlreadyExistsException("User is already registered!");
	    }
	    user.setPassword(passwordEncoder.encode(user.getPassword()));
	    return userRepository.save(user);
	}
	@Override
	public Optional<User> getUserByEmail(String username) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}
	@Override
	public boolean updatePassword(Long id, PasswordUpdateDTO passwordUpdateDTO) throws InvalidPasswordException {
		
		User user = userRepository.findById(id)
				.orElseThrow(()-> new UserNotFoundException("User not found"));
		 // Check if the old password matches the current password
		if(!passwordEncoder.matches(passwordUpdateDTO.getOldPasssword(), user.getPassword()))
		{
			throw new InvalidPasswordException("Invalid password");
		}
		  // Check if the old password and new password are the same
		if(passwordEncoder.matches(passwordUpdateDTO.getNewPassword(),user.getPassword()))
		{
			throw new InvalidPasswordException("New password must differ from the old one");
		}
		 // New password and confirm password don't match
		if(!passwordUpdateDTO.getNewPassword().equals(passwordUpdateDTO.getConfirmPassword()))
		{
			throw new InvalidPasswordException("New password does not match confirm password");
		}
		 // Update the password
		user.setPassword(passwordEncoder.encode(passwordUpdateDTO.getNewPassword()));
		userRepository.save(user);
		return true;
	}

}
