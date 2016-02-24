package com.payfast.endpoint.user;

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

@Path("/users")
public class UserResource {

	private Users users = new HashMapUsersDao();
	
	@GET
	@Path("/{id}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response user(@PathParam("id") Long id) {
		Optional<User> user = users.findBy(id);
		if (user.isPresent()) {
			return Response.ok(user.get()).build();
		}
		return Response.status(Response.Status.NOT_FOUND).build();
	}

	@POST
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response saveNewUser(User user) throws URISyntaxException {
		User userSaved = users.saveNew(user);
		
		Response response = Response
				.created(new URI("http://localhost:8080/users/"+userSaved.getId()))
				.entity(userSaved)
				.type(MediaType.APPLICATION_JSON_TYPE)
				.build();
		
		return response;
	}
}
