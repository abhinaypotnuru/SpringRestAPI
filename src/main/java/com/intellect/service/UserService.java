package com.intellect.service;


import com.intellect.model.User;

public interface UserService {
	
	User findById(long id);
	
	User findByEmailId(String email);
	
	void saveUser(User user);
	
	void updateUser(User user);
	
	void deActivateUser(long id);
	
	boolean isUserExist(User user);
	
}
