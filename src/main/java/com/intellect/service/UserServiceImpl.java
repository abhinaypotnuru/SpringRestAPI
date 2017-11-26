package com.intellect.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.intellect.model.User;
import com.intellect.model.UserStatus;

@Service("userService")
public class UserServiceImpl implements UserService {

	private static final AtomicLong counter = new AtomicLong();

	private static List<User> users;

	static {
		try {
			users = populateDummyUsers();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<User> findAllUsers() {
		return users;
	}

	public User findById(long id) {
		for (User user : users) {
			if (user.getId() == id) {
				return user;
			}
		}
		return null;
	}

	public User findByEmailId(String email) {
		for (User user : users) {
			if (user.getEmail().equalsIgnoreCase(email)) {
				return user;
			}
		}
		return null;
	}

	public void saveUser(User user) {
		user.setId(counter.incrementAndGet());
		users.add(user);
	}

	public void updateUser(User user) {
		int index = users.indexOf(user);
		users.set(index, user);
	}

	public void deActivateUser(long id) {

		for (Iterator<User> iterator = users.iterator(); iterator.hasNext();) {
			User user = iterator.next();
			if (user.getId() == id) {
				user.setStatus(UserStatus.INACTIVE);
			}
		}
	}

	public boolean isUserExist(User user) {
		return findByEmailId(user.getEmail()) != null;
	}

	public void deleteAllUsers() {
		users.clear();
	}

	private static List<User> populateDummyUsers() throws ParseException {
		List<User> users = new ArrayList<User>();
		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
		Date d1 = df.parse("12-MAR-2011");
		Date d2 = df.parse("22-JAN-1999");
		users.add(new User(counter.incrementAndGet(), "Reva", "Jones", "rjones@intellect.com", 12345, d1));
		users.add(new User(counter.incrementAndGet(), "tom", "cruise", "tcruise@intellect.com", 36363, d2));
		users.add(new User(counter.incrementAndGet(), "Jen", "Lopez", "jlopez@intellect.com", 56333, d1));
		users.add(new User(counter.incrementAndGet(), "Alex", "markov", "amarkovs@intellect.com", 87965, d2));
		return users;
	}

}
