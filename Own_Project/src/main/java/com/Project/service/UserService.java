package com.Project.service;

import java.util.Optional;

import com.Project.DTO.PasswordUpdateDTO;
import com.Project.entity.User;
import com.Project.exception.EmailAlreadyExistsException;
import com.Project.exception.InvalidPasswordException;

public interface UserService {

	
	User registerUser(User user) throws EmailAlreadyExistsException;

	Optional<User> getUserByEmail(String username);

	boolean updatePassword(Long id, PasswordUpdateDTO passwordUpdateDTO) throws InvalidPasswordException;

}
