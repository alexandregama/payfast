package com.payfast.endpoint.payment;

import java.util.List;
import java.util.Optional;

public interface Payments {

	Optional<Payment> findBy(Long id);

	Payment saveNew(Payment payment);

	Payment confirm(Payment payment);

	List<Payment> listAll();
	
}
