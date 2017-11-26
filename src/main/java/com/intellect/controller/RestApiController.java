package com.intellect.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.intellect.model.User;
import com.intellect.model.UserStatus;
import com.intellect.service.UserService;
import com.intellect.util.CustomErrorType;

@RestController
@RequestMapping("/api")
public class RestApiController {

	public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);

	@Autowired
	UserService userService;

	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getUser(@PathVariable("id") long id) {
		logger.info("Fetching User with id {}", id);
		User user = userService.findById(id);
		if (user == null) {
			logger.error("User with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("User with id " + id + " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@RequestMapping(value = "/user/", method = RequestMethod.POST)
	public ResponseEntity<User> createUser(@Valid @RequestBody User user, UriComponentsBuilder ucBuilder) {

		logger.info("Creating User : {}", user);
		if (userService.isUserExist(user)) {
			logger.error("Unable to create. A User with name {} already exist", user.getFirstName());
			return new ResponseEntity(
					new CustomErrorType("Unable to create. A User with email " + user.getEmail() + " already exist."),
					HttpStatus.CONFLICT);
		}
		user.setStatus(UserStatus.ACTIVE);
		userService.saveUser(user);

		return new ResponseEntity("User created with userID" + "\t" + user.getId(), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateUser(@PathVariable("id") long id, @RequestBody User user) {
		logger.info("Updating User with id {}", id);

		User currentUser = userService.findById(id);
		System.out.println(currentUser);
		if (currentUser == null) {
			logger.error("Unable to update. User with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to upate. User with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}

		if (user.getPincode() != null) {
			currentUser.setPincode(user.getPincode());
		}
		if (user.getBirthDate() != null) {
			currentUser.setBirthDate(user.getBirthDate());
		}

		userService.updateUser(currentUser);
		return new ResponseEntity<>("User updated with userID" + "\t" + currentUser.getId(), HttpStatus.OK);
	}

	@RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
		logger.info("Fetching & Deleting User with id {}", id);

		User user = userService.findById(id);
		if (user == null) {
			logger.error("Unable to delete. User with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to delete. User with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		userService.deActivateUser(id);
		return new ResponseEntity<>("User deactivated with userID" + "\t" + id, HttpStatus.OK);
	}

}