package eu.vital.management.rest;

import com.fasterxml.jackson.databind.JsonNode;
import eu.vital.management.security.SecurityService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

@Path("/security/")
@RequestScoped
public class LoginRestService {

	@Inject
	SecurityService securityService;

	@GET
	@Path("/logged-on")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getLoggedOnUser(@CookieParam(SecurityService.COOKIE_NAME) Cookie authCookie) throws Exception {
		if (authCookie == null) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}

		JsonNode user = securityService.getLoggedOnUser(authCookie.getValue());
		if (user == null) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}

		return Response.ok(user).build();
	}

	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(@FormParam("name") String username, @FormParam("password") String password) throws Exception {
		String authToken = securityService.login(username, password);
		if (authToken == null) {
			return Response.status(Response.Status.FORBIDDEN).build();
		}

		JsonNode userData = securityService.getLoggedOnUser(authToken);

		return Response.ok(userData).
				cookie(new NewCookie(SecurityService.COOKIE_NAME, authToken))
				.build();
	}

}
