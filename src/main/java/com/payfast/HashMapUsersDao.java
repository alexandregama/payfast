package com.payfast;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HashMapUsersDao implements Users {

	private Map<Long, User> database = new HashMap<>();
	
	public HashMapUsersDao() {
		database.put(1L, new User(1L, "Alexandre Gama"));
		database.put(2L, new User(2L, "Mathew Perry"));
		database.put(3L, new User(3L, "Jessica Alba"));
		database.put(4L, new User(4L, "Carlos Brando"));
	}
	
	@Override
	public Optional<User> findBy(Long id) {
		return Optional.ofNullable(database.get(id));
	}

}
