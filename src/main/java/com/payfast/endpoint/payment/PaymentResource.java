package com.payfast.endpoint.payment;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/payments")
public class PaymentResource {

	private Payments payments = new HashMapPaymentsDao();
	
	@GET
	@Path("/{id}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response payment(@PathParam("id") Long id) {
		System.out.println("Retrieving paymento from id=" + id);
		
		Optional<Payment> payment = payments.findBy(id);
		if (payment.isPresent()) {
			return Response.ok(payment.get()).build();
		}
		return Response.status(404).build();
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveNew(Payment payment) throws URISyntaxException {
		Payment savedPayment = payments.saveNew(payment);
		
		Response response = Response
				.created(new URI("http://localhost:8080/payments/" + savedPayment.getId()))
				.build();
		
		System.out.println("Payment created: " + savedPayment);
		return response;
	}
	
}
