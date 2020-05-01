package spotify;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.json.simple.JSONArray;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyHttpManager;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;


import spotify.Users.User;

public class SpotifyAPI {
//https://accounts.spotify.com/authorize?response_type=code&client_id=2d77bc05e0f841679c7455f113130e4f&scope=user-top-read user-read-playback-state&redirect_uri=https://www.spotify.com/us/

	private static final String clientId = "2d77bc05e0f841679c7455f113130e4f";
	private static final String clientSecret = "f36176b01d21452fadc0eeedf04e275b";
	private static final URI redirectUri = SpotifyHttpManager.makeUri("https://www.spotify.com/us/");

	private static String code = "";
	
	private static SpotifyApi spotifyApi = new SpotifyApi.Builder().setClientId(clientId)
			.setClientSecret(clientSecret).setRedirectUri(redirectUri).build();

	private static AuthorizationCodeRequest authorizationCodeRequest = null;

	public static void setCode(String code) {
		SpotifyAPI.code = code;
		authorizationCodeRequest = spotifyApi.authorizationCode(code).build();

	}
	
	public static void authorizationCode_Sync() {
		try {
			final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();

			// Set access and refresh token for further "spotifyApi" object usage
			spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
			spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());

			System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
		} catch (IOException | SpotifyWebApiException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public static void getAPI(User user) {
		ArrayList<String> topArtists = new ArrayList<String>();
		ArrayList<String> topTracks = new ArrayList<String>();
		String currentlyPlaying = "";
		setCode(user.getAccessToken());
		authorizationCode_Sync();
		
		JSONArray artists = new JSONArray();
		JSONArray toptracks = new JSONArray();
		String current = "";
		
		try {
			GetCurrentSong getCurrentSong = new GetCurrentSong(spotifyApi);
			GetTopSongs getTopSongs = new GetTopSongs(spotifyApi);
			GetTopArtists getTopArtists = new GetTopArtists(spotifyApi);

			Future<ArrayList<String>> songs;
			Future<ArrayList<String>> getartists;
			
			
			
			Future<String> currentSong;
			

			ExecutorService executor = Executors.newFixedThreadPool(3);
			songs = executor.submit(getTopSongs);
			getartists = executor.submit(getTopArtists);
			currentSong = executor.submit(getCurrentSong);
			
			currentlyPlaying = currentSong.get();
			topTracks = songs.get();
			topArtists = getartists.get();
			executor.shutdown();
			
			for (int i = 0; i < topArtists.size(); ++i) {
				//System.out.println("Your #" + (i + 1) + " artist over the last month is: " + topArtists.get(i));
				artists.add(topArtists.get(i));
			}
			//System.out.println();
			for (int i = 0; i < topTracks.size(); ++i) {
				//System.out.print("Your #" + (i + 1) + " song over the last month is: ");
				//System.out.println(topTracks.get(i));
				toptracks.add(topTracks.get(i));
			}
			
			//System.out.println();
			if (currentlyPlaying!= null && !currentlyPlaying.equalsIgnoreCase("")) {
				//System.out.println(currentlyPlaying + " is currently playing");
				current = currentlyPlaying;
			}
			
		} catch (InterruptedException e) {
			System.out.println("Interrupted in API: " + e.getMessage());
		} catch (ExecutionException e) {
			System.out.println("Execution Exception in API: " + e.getMessage());
		} catch (NullPointerException e) {
			System.out.println("Nullpoint Exception in API: " + e.getMessage());
		}
		user.setUserJson(artists, toptracks, current);
		
	}

}
