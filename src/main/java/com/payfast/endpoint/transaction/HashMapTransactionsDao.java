package com.payfast.endpoint.transaction;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.payfast.endpoint.payment.HashMapPaymentsDao;
import com.payfast.endpoint.payment.Payments;
import com.payfast.endpoint.user.HashMapUsersDao;
import com.payfast.endpoint.user.Users;

public class HashMapTransactionsDao implements Transactions {

	private Map<Long, Transaction> database = new HashMap<>();
	
	private Users users = new HashMapUsersDao();
	
	private Payments payments = new HashMapPaymentsDao();
	
	public HashMapTransactionsDao() {
		long id1 = 1L;
		database.put(id1, new Transaction(id1, users.findBy(id1).get(), payments.findBy(id1).get()));
		
		long id2 = 2L;
		database.put(id2, new Transaction(id2, users.findBy(id2).get(), payments.findBy(id2).get()));
		
		long id3 = 3L;
		database.put(id3, new Transaction(id3, users.findBy(id3).get(), payments.findBy(id3).get()));
	}
	
	@Override
	public Optional<Transaction> findBy(Long id) {
		return Optional.ofNullable(database.get(id));
	}

}
