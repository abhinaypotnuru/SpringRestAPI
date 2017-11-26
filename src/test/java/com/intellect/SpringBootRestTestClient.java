package com.intellect;

import java.net.URI;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.client.RestTemplate;

import com.intellect.model.User;

public class SpringBootRestTestClient {

	public static final String REST_SERVICE_URI = "http://localhost:8080/SpringBootRestApi/api";

	private static void createUser() throws ParseException {
		RestTemplate restTemplate = new RestTemplate();
		DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
		Date d1 = df.parse("12-MAR-2011");
		User user = new User(1, "Alex", "markov", "amarkovs@intellect.com", 87965, d1);
		URI uri = restTemplate.postForLocation(REST_SERVICE_URI + "/user/", user, User.class);
	}

	private static void updateUser() throws ParseException {
		RestTemplate restTemplate = new RestTemplate();
		DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
		Date d1 = df.parse("12-MAR-2011");
		User user = new User(1, "Alex", "markov", "amarkovs@intellect.com", 87965, d1);
		restTemplate.put(REST_SERVICE_URI + "/user/1", user);
	}

	private static void deleteUser() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.delete(REST_SERVICE_URI + "/user/3");
	}

	
	public static void main(String args[]) {

		try {
			createUser();
			updateUser();
			deleteUser();
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}
}