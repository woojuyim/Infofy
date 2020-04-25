package spotify;

//import java.util.ArrayList;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import spotify.Users.User;

//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;

@Path("/")
public class UserService {

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
					.entity(Users.userMap.get(username).userJSON).build();
		}

		else {
			return Response.status(404).header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Credentials", "true")
					.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
					.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
					.entity("Username not available").build();
		}
	}

	@GET
	@Path("/user/{username}")
	@Produces("application/json")
	public static Response getUser(@PathParam("username") String username) {
		if (Users.userMap.containsKey(username)) {
			return Response.status(200).header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Credentials", "true")
					.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
					.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
					.entity(Users.userMap.get(username).userJSON).build();

		}

		else {
			return Response.status(404).header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Credentials", "true")
					.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
					.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
					.entity("User not found").build();
		}
	}

	// Push Access Token
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

	@GET
	@Path("/user/{username}/topSongs")
	@Produces("application/json")
	public static Response getTopSongs(@PathParam("username") String username) {
		if (Users.userMap.containsKey(username)) {
			return Response.status(200).header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Credentials", "true")
					.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
					.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
					.entity(Users.userMap.get(username).topSongsJSON).build();
		}

		else {
			return Response.status(404).header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Credentials", "true")
					.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
					.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
					.entity("User not found").build();
		}
	}

	@GET
	@Path("/user/{username}/topAlbums")
	@Produces("application/json")
	public static Response getTopAlbums(@PathParam("username") String username) {
		if (Users.userMap.containsKey(username)) {
			return Response.status(200).header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Credentials", "true")
					.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
					.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
					.entity(Users.userMap.get(username).topAlbumsJSON).build();
		} else {
			return Response.status(404).header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Credentials", "true")
					.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
					.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
					.entity("User not found").build();
		}
	}

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

//	@SuppressWarnings("static-access")
//	@GET
//	@Path("/user/{username}/topSongs")
//	@Produces("application/json")
//	public String getTopSongs(@PathParam("username") String username) {
//		return User.userMap.get(username).topSongsJSON.toJSONString();
//	}
//	
//	@SuppressWarnings("static-access")
//	@GET
//	@Path("/user/{username}/topAlbums")
//	@Produces("application/json")
//	public String getTopAlbums(@PathParam("username") String username) {
//		return User.userMap.get(username).topAlbumsJSON.toJSONString();
//	}
//	
//	@SuppressWarnings("static-access")
//	@GET
//	@Path("/user/{username}/topAlbums")
//	@Produces("application/json")
//	public String getTopArtists(@PathParam("username") String username) {
//		return User.userMap.get(username).topArtistsJSON.toJSONString();
//	}

//	@PUT
//	@Path("/user")
//	@Produces("application/json")
//	public String setUser(@QueryParam("firstName") String firstName,
//							@QueryParam("lastName") String lastName,
//							@QueryParam("following") ArrayList<User> following,
//							@QueryParam("topSongs") ArrayList<Song> topSongs,
//							@QueryParam("songCount") int songCount) {
//		User.firstName = firstName;
//		User.lastName = lastName;
//		User.following = following;
//		User.topSongs = topSongs;
//		User.songCount = songCount;
//		String pattern = "{ \"firstName\": \"%s\", \"lastName\": \"%s\", \"following\": \"%s\", \"topSongs\": \"%s\","
//				+ "\"songCount\": \"%s\"}";
//		return String.format(pattern, User.firstName, User.lastName, User.following, User.topSongs, User.songCount);
//	}

}
