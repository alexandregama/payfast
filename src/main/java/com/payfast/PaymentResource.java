package com.payfast;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/payments")
public class PaymentResource {

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String payment(@PathParam("id") Long id) {
		System.out.println("Retrieving paymento from id=" + id);
		return "{id: 1, value: 10}";
	}
	
}
