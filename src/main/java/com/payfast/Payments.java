package com.payfast;

import java.util.Optional;

public interface Payments {

	Optional<Payment> findBy(Long id);
	
}
