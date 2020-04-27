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
	// https://accounts.spotify.com/authorize?response_type=code&client_id=2d77bc05e0f841679c7455f113130e4f&scope=user-top-read user-read-playback-state&redirect_uri=https://www.spotify.com/us/

	private static ArrayList<String> topArtists = new ArrayList<String>();
	private static ArrayList<String> topTracks = new ArrayList<String>();
	private static String currentlyPlaying = "";

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

//	public static void getUsersTopArtists_Sync() {
//		final String accessToken = spotifyApi.getAccessToken();
//
//		final SpotifyApi spotifyApi = new SpotifyApi.Builder().setAccessToken(accessToken).build();
//		final GetUsersTopArtistsRequest getUsersTopArtistsRequest = spotifyApi.getUsersTopArtists().time_range("short_term").build();
//
//		try {
//			final Paging<Artist> artistPaging = getUsersTopArtistsRequest.execute();
//
//			for (int i = 0; i < 5; i++) {
//				Artist artist = (Artist) Array.get(artistPaging.getItems(), i);
//				String artistName = artist.getName();
//				topArtists.add(artistName);
//			}
//		} catch (IOException | SpotifyWebApiException | NullPointerException| ArrayIndexOutOfBoundsException e) {
//			System.out.println("Error in getTopArtists: " + e.getMessage());
//		} 
//	}
//
//	public static void getUsersTopTracks_Sync() {
//		final GetUsersTopTracksRequest getUsersTopTracksRequest = spotifyApi.getUsersTopTracks().time_range("short_term").build();
//		final String accessToken = spotifyApi.getAccessToken();
//
//		final SpotifyApi spotifyApi = new SpotifyApi.Builder().setAccessToken(accessToken).build();
//
//		try {
//			final Paging<Track> trackPaging = getUsersTopTracksRequest.execute();
//
//			ArrayList<Track> tracks = new ArrayList<Track>();
//			for (int i = 0; i < 10; i++) {
//				Track track = (Track) Array.get(trackPaging.getItems(), i);
//				tracks.add(track);
//			}
//			for (int i = 0; i < tracks.size(); i++) {
//				String name = "";
//				name += tracks.get(i).getName() + " by ";
//				for (int j = 0; j < tracks.get(i).getArtists().length; j++) {
//					ArtistSimplified art = (ArtistSimplified) Array.get(tracks.get(i).getArtists(), j);
//					String artName = art.getName();
//					if (tracks.get(i).getArtists().length == 1) {
//						name += artName;
//					} else if (j == 0 && tracks.get(i).getArtists().length == 2) {
//						name += artName + " ";
//					} else if ((j + 1) == tracks.get(i).getArtists().length && j != 0) {
//						name += "and " + artName;
//					} else {
//						name += artName + ", ";
//					}
//				}
//				topTracks.add(name);
//			}
//
//		} catch (IOException | SpotifyWebApiException | NullPointerException | ArrayIndexOutOfBoundsException  e) {
//			System.out.println("Error in getTopSongs: " + e.getMessage());
//		} 
//	}
//
//	public static void getArtist_Sync() {
//		final String accessToken = spotifyApi.getAccessToken();
//		final String id = "31W5EY0aAly4Qieq6OFu6I";
//		final GetArtistRequest getArtistRequest = spotifyApi.getArtist(id).build();
//
//		try {
//			final Artist artist = getArtistRequest.execute();
//
//			System.out.println("Name: " + artist.getName());
//		} catch (IOException | SpotifyWebApiException | NullPointerException e) {
//			System.out.println("Error in getArtist: " + e.getMessage());
//		}
//	}
//
//	public static void getInformationAboutUsersCurrentPlayback_Sync() {
//		final GetInformationAboutUsersCurrentPlaybackRequest getInformationAboutUsersCurrentPlaybackRequest = spotifyApi
//				.getInformationAboutUsersCurrentPlayback().build();
//		Track track = null;
//
//		try {
//			final CurrentlyPlayingContext currentlyPlayingContext = getInformationAboutUsersCurrentPlaybackRequest
//					.execute();
//			if (currentlyPlayingContext.getIs_playing()) {
//				track = currentlyPlayingContext.getItem();
//			}
//
//			if (track != null) {
//				String name = "";
//				name += track.getName() + " by ";
//				for (int j = 0; j < track.getArtists().length; j++) {
//					ArtistSimplified art = (ArtistSimplified) Array.get(track.getArtists(), j);
//					String artName = art.getName();
//					if (track.getArtists().length == 1) {
//						name += artName;
//					} else if (j == 0 && track.getArtists().length == 2) {
//						name += artName + " ";
//					} else if ((j + 1) == track.getArtists().length && j != 0) {
//						name += "and " + artName;
//					} else {
//						name += artName + ", ";
//					}
//				}
//				currentlyPlaying = name;
//			}
//		} catch (IOException | SpotifyWebApiException | NullPointerException e) {
//			System.out.println("Error in getCurrentPlayback: " + e.getMessage());
//		}
//	}
	@SuppressWarnings("unchecked")
	public static void getAPI(User user) {
		//Clears entries
		topArtists.clear();
		topTracks.clear();
		currentlyPlaying = "";
		setCode(user.getAccessToken());
		authorizationCode_Sync();
		
		
		try {
			GetCurrentSong getCurrentSong = new GetCurrentSong(spotifyApi);
			GetTopSongs getTopSongs = new GetTopSongs(spotifyApi);
			GetTopArtists getTopArtists = new GetTopArtists(spotifyApi);

			Future<String> currentSong;
			Future<ArrayList<String>> songs;
			Future<ArrayList<String>> getartists;

			ExecutorService executor = Executors.newFixedThreadPool(3);
			songs = executor.submit(getTopSongs);
			getartists = executor.submit(getTopArtists);
			currentSong = executor.submit(getCurrentSong);
			
			currentlyPlaying = currentSong.get();
			topTracks = songs.get();
			topArtists = getartists.get();
			executor.shutdown();

			
			JSONArray artists = new JSONArray();
			JSONArray toptracks = new JSONArray();
			String current = "";
			
			for (int i = 0; i < topArtists.size(); i++) {
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
			} else {
				//System.out.println("Nothing is currently playing");
			}
			
			user.setUserJson(artists, toptracks, current);
		} catch (InterruptedException e) {
			System.out.println("Interrupted in API: " + e.getMessage());
		} catch (ExecutionException e) {
			System.out.println("Execution Exception in API: " + e.getMessage());
		} catch (NullPointerException e) {
			System.out.println("Nullpoint Exception in API: " + e.getMessage());
		}
		
		
	}

}
