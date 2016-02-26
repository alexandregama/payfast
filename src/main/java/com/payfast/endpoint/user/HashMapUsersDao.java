package com.payfast.endpoint.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class HashMapUsersDao implements Users {

	private static Map<Long, User> database = new HashMap<>();
	
	static {
		database.put(1L, new User(1L, "Alexandre Gama"));
		database.put(2L, new User(2L, "Mathew Perry"));
		database.put(3L, new User(3L, "Jessica Alba"));
		database.put(4L, new User(4L, "Carlos Brando"));
	}
	
	@Override
	public Optional<User> findBy(Long id) {
		return Optional.ofNullable(database.get(id));
	}

	@Override
	public User saveNew(User user) {
		Long newId = database.keySet()
			.stream()
			.max(Long::compareTo)
			.get() + 1;
		
		user.setId(newId);
		database.put(newId, user);
		
		return user;
	}

	@Override
	public User update(User user) {
		database.put(user.getId(), user);
		return user;
	}

	@Override
	public List<User> findAll() {
		Collection<User> list = database.values();
		
		return new ArrayList<>(list);
	}

}
