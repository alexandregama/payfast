package com.payfast.endpoint.user;

import java.util.List;
import java.util.Optional;

public interface Users {

	Optional<User> findBy(Long id);

	User saveNew(User user);

	User update(User user);

	List<User> findAll();
	
}
