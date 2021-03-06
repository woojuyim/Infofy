package infofy;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Users {	
	static HashMap<String, User> userMap = new HashMap<String, User>();
	public static class User {
		public String username;
		public String password;
		public String firstName;
		public String lastName;
		public String token;
		
		public String currentlyPlaying;
		public ArrayList<String> topArtists;
		public ArrayList<String> topSongs;
		
		
		public JSONObject userJSON;
		public JSONArray followingJSON;
		public JSONArray topSongsJSON;
		public JSONArray topAlbumsJSON;
		public JSONArray topArtistsJSON;

		@SuppressWarnings("unchecked")
		public User() {
			userJSON = new JSONObject();
			topSongsJSON = new JSONArray();
			topArtistsJSON = new JSONArray();
			token = "";
			currentlyPlaying = "";
			userJSON.put("topSongs", topSongsJSON);
			userJSON.put("topArtists", topArtistsJSON);
			userJSON.put("currentlyPlaying", currentlyPlaying);

			
		}
		@SuppressWarnings("unchecked")
		public User(String username, String password, String firstName, String lastName) {
			this.username = username;
			this.password = password;
			this.firstName = firstName;
			this.lastName = lastName;
			token = "";
			currentlyPlaying = "";
			userJSON = new JSONObject();
			topSongsJSON = new JSONArray();
			topArtistsJSON = new JSONArray();

			userJSON.put("username", username);
			userJSON.put("password", password);
			userJSON.put("firstName", firstName);
			userJSON.put("lastName", lastName);
			userJSON.put("topSongs", topSongsJSON);
			userJSON.put("topArtists", topArtistsJSON);
			userJSON.put("currentlyPlaying", currentlyPlaying);

		}
		public void setUserData(ArrayList<String> artists, ArrayList<String> songs, String current) {
			topArtists = artists;
			topSongs = songs;
			currentlyPlaying = current;
		}
		@SuppressWarnings("unchecked")
		public void setUserJson(JSONArray artists, JSONArray songs, String current) {
			topSongsJSON = songs;
			topArtistsJSON = artists;
			currentlyPlaying = current;
			userJSON.replace("topSongs", topSongsJSON);
			userJSON.replace("topArtists", topArtistsJSON);
			userJSON.replace("currentlyPlaying", current);
		}

		public String getUsername() {
			return this.username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return this.password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getFirstName() {
			return this.firstName;
		}

		@SuppressWarnings("unchecked")
		public void setFirstName(String firstName) {
			this.firstName = firstName;
			userJSON.replace("firstName", this.firstName);
		}

		public String getLastName() {
			return this.lastName;
		}

		@SuppressWarnings("unchecked")
		public void setLastName(String lastName) {
			this.lastName = lastName;
			userJSON.replace("lastName", this.lastName);
		}

		@SuppressWarnings("unchecked")
		public void setAccessToken(String token) {
			this.token = token;
			userJSON.replace("token", this.token);
		}

		public String getAccessToken() {
			return token;
		}
	}
}
