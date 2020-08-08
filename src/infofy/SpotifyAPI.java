package infofy;

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

import infofy.Users.User;

public class SpotifyAPI {

	private static final String clientId = "75fbbc3b3d8b4bc2ba1e1c197221602e";
	private static final String clientSecret = "582e4989a396466a87edd6ac2f33dd59";
	private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8080/infofy/data");

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
			
			user.setUserData(topArtists, topTracks, currentlyPlaying);

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
