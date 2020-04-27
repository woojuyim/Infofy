package spotify;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.CrossOrigin;

import spotify.Users.User;

//@CrossOrigin(origins = "*", allowedHeaders = "*")
@Path("/")
public class UserService {

	// @CrossOrigin(origins = "*", allowedHeaders = "*")
	@GET
	@Path("/create")
	@Produces("application/json")
	public static Response createUser(@QueryParam("user") String username, @QueryParam("pw") String password) {
		if (!Users.userMap.containsKey(username)) {
			User newUser = new User(username, password, "", "");
			Users.userMap.put(username, newUser);

			return Response.status(200).header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Credentials", "true")
					.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
					.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
					.entity(Users.userMap.get(username).userJSON.toJSONString()).build();
		}

		else {
			return Response.status(404).header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Credentials", "true")
					.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
					.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
					.entity("Username not available").build();
		}
	}

	// @CrossOrigin(origins = "*", allowedHeaders = "*")
	@GET
	@Path("/user/{username}")
	@Produces("application/json")
	public static Response getUser(@PathParam("username") String username) {
		if (Users.userMap.containsKey(username)) {
			return Response.status(200).header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Credentials", "true")
					.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
					.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
					.entity(Users.userMap.get(username).userJSON.toJSONString()).build();

		}

		else {
			return Response.status(404).header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Credentials", "true")
					.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
					.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
					.entity("User not found").build();
		}
	}

	// Push Access Token with no user
	// @CrossOrigin(origins = "*", allowedHeaders = "*")
	@GET
	@Path("/token")
	@Produces("application/json")
	public static Response getValue(@QueryParam("code") String token) {
		if (token != null) {
			User user = new User();
			user.setAccessToken(token);
			SpotifyAPI.getAPI(user);
			return Response.status(200).header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Credentials", "true")
					.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
					.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
					.entity(user.userJSON.toJSONString()).build();
		} else {
			return Response.status(404).header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Credentials", "true")
					.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
					.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD").entity(token)
					.build();
		}
	}

	// Push Access Token
	// @CrossOrigin(origins = "*", allowedHeaders = "*")
	@GET
	@Path("/user/{username}/token")
	@Produces("application/json")
	public static Response setToken(@PathParam("username") String username, @QueryParam("code") String token) {
		if (Users.userMap.containsKey(username)) {
			Users.userMap.get(username).setAccessToken(token);
			SpotifyAPI.getAPI(Users.userMap.get(username));
			return Response.status(200).header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Credentials", "true")
					.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
					.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
					.entity(Users.userMap.get(username).userJSON.toJSONString()).build();
		} else {
			return Response.status(404).header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Credentials", "true")
					.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
					.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD").entity(token)
					.build();
		}
	}

	// @CrossOrigin(origins = "*", allowedHeaders = "*")
	@GET
	@Path("/user/{username}/topSongs")
	@Produces("application/json")
	public static Response getTopSongs(@PathParam("username") String username) {
		if (Users.userMap.containsKey(username)) {
			return Response.status(200).header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Credentials", "true")
					.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
					.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
					.entity(Users.userMap.get(username).topSongsJSON.toJSONString()).build();
		}

		else {
			return Response.status(404).header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Credentials", "true")
					.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
					.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
					.entity("User not found").build();
		}
	}

	// @CrossOrigin(origins = "*", allowedHeaders = "*")
	@GET
	@Path("/user/{username}/topAlbums")
	@Produces("application/json")
	public static Response getTopAlbums(@PathParam("username") String username) {
		if (Users.userMap.containsKey(username)) {
			return Response.status(200).header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Credentials", "true")
					.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
					.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
					.entity(Users.userMap.get(username).topAlbumsJSON.toJSONString()).build();
		} else {
			return Response.status(404).header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Credentials", "true")
					.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
					.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
					.entity("User not found").build();
		}
	}

	// @CrossOrigin(origins = "*", allowedHeaders = "*")
	@GET
	@Path("/map")
	@Produces("text/plain")
	public static String getMap() {
		String users = "";

		if (Users.userMap.isEmpty()) {
			return "map is empty";
		}

		else {
			for (int i = 0; i < Users.userMap.size(); i++) {
				users = Users.userMap.toString();
			}
			return users;
		}

	}
}