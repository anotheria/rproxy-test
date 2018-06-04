package net.anotheria.service;
 
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
 
@Path("/hello")
public class HelloWorldService {
 
	@GET
	public Response getMsg() {
 
		String output = "Service called.";
 
		return Response.status(200).entity(output).build();
 
	}
 
}