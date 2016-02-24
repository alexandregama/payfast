package com.payfast.endpoint.user;

import java.util.Optional;

public interface Users {

	Optional<User> findBy(Long id);
}
