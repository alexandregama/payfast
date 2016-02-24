package com.payfast;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HashMapPaymentsDao implements Payments {

	private Map<Long, Payment> database = new HashMap<>();
	
	public HashMapPaymentsDao() {
		database.put(1L, new Payment(1L, "INPROGRESS"));
		database.put(2L, new Payment(2L, "INPROGRESS"));
		database.put(3L, new Payment(3L, "CANCELED"));
		database.put(4L, new Payment(4L, "CANCELED"));
		database.put(5L, new Payment(5L, "APPROVED"));
		database.put(6L, new Payment(6L, "APPROVED"));
	}
	
	@Override
	public Optional<Payment> findBy(Long id) {
		return Optional.ofNullable(database.get(id));
	}

}
