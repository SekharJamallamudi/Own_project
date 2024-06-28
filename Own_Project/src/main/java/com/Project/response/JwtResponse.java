package com.Project.response;

import java.util.Map;

import com.Project.entity.User;

public class JwtResponse {

    private final Map<String, Object> token;
    private final String message;
	private User user;
   
    public JwtResponse(Map<String, Object> token2, String message, User user) {
        this.token = token2;
        this.message = message;
		this.setUser(user);
    }

	public Map<String, Object> getToken() {
		return token;
	}

	public String getMessage() {
		return message;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
    // getters
}