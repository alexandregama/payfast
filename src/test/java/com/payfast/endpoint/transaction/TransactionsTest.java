package com.payfast.endpoint.transaction;

import static org.junit.Assert.*;

import org.junit.Test;

public class TransactionsTest {

	@Test
	public void shouldRetrieveATransactionFromItsId() throws Exception {
		Transactions transactions = new HashMapTransactionsDao();
		
		Transaction transaction = transactions.findBy(1L).get();
		
		assertEquals(1L, transaction.getId(), 0);
		assertEquals(1L, transaction.getUser().getId(), 0);
		assertEquals(1L, transaction.getPayment().getId(), 0);
	}
	
}
