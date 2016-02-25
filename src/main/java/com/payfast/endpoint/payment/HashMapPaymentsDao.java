package com.payfast.endpoint.payment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class HashMapPaymentsDao implements Payments {

	private static Map<Long, Payment> database = new HashMap<>();
	
	static {
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

	@Override
	public Payment saveNew(Payment payment) {
		Long nextId = database.keySet().stream().max(Long::compareTo).get() + 1;
		
		payment.setId(nextId);
		payment.started();
		database.put(nextId, payment);
		
		return payment;
	}

	@Override
	public Payment confirm(Payment payment) {
		Payment confirmedPayment = database.get(payment.getId());
		confirmedPayment.setStatus(payment.getStatus());
		database.put(payment.getId(), confirmedPayment);
		return null;
	}

	@Override
	public List<Payment> listAll() {
		Collection<Payment> values = database.values();
		
		return new ArrayList<>(values);
	}

}
