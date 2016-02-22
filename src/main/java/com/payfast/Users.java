package com.payfast;

import java.util.Optional;

public interface Users {

	Optional<User> findBy(Long id);
}
