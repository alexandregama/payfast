package com.payfast.endpoint.transaction;

import java.util.Optional;

public interface Transactions {

	Optional<Transaction> findBy(Long id);
	
}
