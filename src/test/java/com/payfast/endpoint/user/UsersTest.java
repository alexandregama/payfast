package com.payfast.endpoint.user;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class UsersTest {

	@Test
	public void shouldSaveANewUser() throws Exception {
		Users users = new HashMapUsersDao();
		
		User user = new User("Alexandre Gama");
		User savedUser = users.saveNew(user);
		
		assertEquals(5L, savedUser.getId(), 0);
	}
	
}
