package com.payfast.endpoint.user;

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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.jboss.resteasy.annotations.cache.Cache;

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
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/cached/{id}")
	@Cache(maxAge=50_000)
	public Response findCachedUserBy(@PathParam("id") Long id, @Context Request request) {
		Optional<User> user = users.findBy(id);
		
		if (user.isPresent()) {
			
			EntityTag etag = new EntityTag(user.get().getName()); 
			ResponseBuilder builder = request.evaluatePreconditions(etag);
			
			if (builder == null) {
				builder = Response.ok(user.get());
				builder.tag(etag);
			} else {
				builder = Response.notModified();
			}
			
			//We can use CacheControl from JAX-RS or @Cache from RESTEasy
//			CacheControl cache = new CacheControl();
//			cache.setMaxAge(50_000);
//			builder.cacheControl(cache);
			
			return builder.build();
		}
		return Response.status(Response.Status.NOT_FOUND).build();
	}

	@POST
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response saveNewUser(User user) throws URISyntaxException {
		System.out.println(user);
		User userSaved = users.saveNew(user);
		
		Response response = Response
				.created(new URI("http://localhost:8080/users/"+userSaved.getId()))
				.entity(userSaved)
				.type(MediaType.APPLICATION_JSON_TYPE)
				.build();
		
		return response;
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public Response update(User user) {
		User updatedUser = users.update(user);
		
		Response response = Response
				.ok(updatedUser)
				.build();
		
		return response;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAll() {
		List<User> list = users.findAll();
		
		Response response = Response
				.ok(list)
				.build();
		
		return response;
	}
}
