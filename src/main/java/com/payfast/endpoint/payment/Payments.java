package com.payfast.endpoint.payment;

import java.util.Optional;

public interface Payments {

	Optional<Payment> findBy(Long id);
	
}
