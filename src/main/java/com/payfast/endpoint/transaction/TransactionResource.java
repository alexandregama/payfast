package com.payfast.endpoint.transaction;

import java.util.Optional;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/transactions")
public class TransactionResource {

	private Transactions transactions = new HashMapTransactionsDao();
	
	@GET
	@Path("/{id}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response findBy(@PathParam("id") Long id) {
		Optional<Transaction> transaction = transactions.findBy(id);
		if (transaction.isPresent()) {
			return Response
					.ok(transaction.get())
					.build();
		}
		return Response.status(Response.Status.NOT_FOUND).build();
	}
	
}
