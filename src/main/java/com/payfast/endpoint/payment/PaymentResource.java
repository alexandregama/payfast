package com.payfast.endpoint.payment;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.providers.jaxb.Formatted;

@Path("/payments")
public class PaymentResource {

	private Payments payments = new HashMapPaymentsDao();
	
	public static final String PAYMENT_RESOURCE_URI = "http://localhost:8080/payfast/payments/"; 
	
	@GET
	@Path("/{id}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Formatted
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
	@Formatted
	public Response saveNew(Payment payment) throws URISyntaxException {
		Payment savedPayment = payments.saveNew(payment);
		
		Response response = Response
				.created(new URI(PAYMENT_RESOURCE_URI + savedPayment.getId()))
				.entity(savedPayment)
				.build();
		
		System.out.println("Payment created: " + savedPayment);
		return response;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Formatted
	public Response payments() {
		List<Payment> list = payments.listAll();
		return Response.ok(list).build();
	}
	
	@PUT
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Formatted
	public Response confirmPayment(Payment payment) {
		payment.confirm();
		Payment savedPayment = payments.confirm(payment);
		
		Response response = Response
				.ok()
				.build();
		
		System.out.println("Payment confirmed: " + savedPayment);
		return response;
	}
	
}
