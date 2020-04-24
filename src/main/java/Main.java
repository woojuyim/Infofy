import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URI;
import java.util.ArrayList;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyHttpManager;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.model_objects.specification.Artist;
import com.wrapper.spotify.model_objects.specification.ArtistSimplified;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRefreshRequest;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import com.wrapper.spotify.requests.data.artists.GetArtistRequest;
import com.wrapper.spotify.requests.data.personalization.simplified.GetUsersTopArtistsRequest;
import com.wrapper.spotify.requests.data.personalization.simplified.GetUsersTopTracksRequest;

public class Main {
	// https://accounts.spotify.com/authorize?response_type=code&client_id=2d77bc05e0f841679c7455f113130e4f&scope=user-top-read&redirect_uri=https://www.spotify.com/us/

	private static ArrayList<String> topArtists = new ArrayList<String>();
	private static ArrayList<String> topTracks = new ArrayList<String>();

	private static final String clientId = "2d77bc05e0f841679c7455f113130e4f";
	private static final String clientSecret = "f36176b01d21452fadc0eeedf04e275b";
	private static final URI redirectUri = SpotifyHttpManager.makeUri("https://www.spotify.com/us/");

	private static final String code = "AQCg842tuMZUWSNf4hedNqJGST5Tygv7UQoc95RmdPa-maNZkVdNkDg0J6NS2DIRj0z2fObLHNvGAMlblpY8NvExIFbiUkRwpZpceT8sDnnPAGkW_0Lj7eMdZlFw_F7pnZBdNn1XAWe_XQJ2tOjWFNYW8kgGTyY5L9W-SGkBaBXlyrtiPLp7LxQXGBGV9tyIeuTucdDxGP7QvBvUGXms2w";
	
	private static final SpotifyApi spotifyApi = new SpotifyApi.Builder().setClientId(clientId)
			.setClientSecret(clientSecret).setRedirectUri(redirectUri).build();

	private static final AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(code).build();

	public static void authorizationCode_Sync() {
		try {
			final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();

			// Set access and refresh token for further "spotifyApi" object usage
			spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
			spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());

			// System.out.println("Expires in: " +
			// authorizationCodeCredentials.getExpiresIn());
		} catch (IOException | SpotifyWebApiException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	public static void getUsersTopArtists_Sync() {
		final String accessToken = spotifyApi.getAccessToken();

		final SpotifyApi spotifyApi = new SpotifyApi.Builder().setAccessToken(accessToken).build();
		final GetUsersTopArtistsRequest getUsersTopArtistsRequest = spotifyApi.getUsersTopArtists().build();

		try {
			final Paging<Artist> artistPaging = getUsersTopArtistsRequest.execute();
			// System.out.println("Artist Total: " + artistPaging.getTotal());

			for (int i = 0; i < 5; i++) {
				Artist artist = (Artist) Array.get(artistPaging.getItems(), i);
				String artistName = artist.getName();
				topArtists.add(artistName);
			}
		} catch (IOException | SpotifyWebApiException e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public static void getUsersTopTracks_Sync() {
		final GetUsersTopTracksRequest getUsersTopTracksRequest = spotifyApi.getUsersTopTracks().build();
		final String accessToken = spotifyApi.getAccessToken();

		final SpotifyApi spotifyApi = new SpotifyApi.Builder().setAccessToken(accessToken).build();

		try {
			final Paging<Track> trackPaging = getUsersTopTracksRequest.execute();
			// System.out.println("Track Total: " + trackPaging.getTotal());

			ArrayList<Track> tracks = new ArrayList<Track>();
			for (int i = 0; i < 5; i++) {
				Track track = (Track) Array.get(trackPaging.getItems(), i);
				tracks.add(track);
			}
			for (int i = 0; i < tracks.size(); i++) {
				String name = "";
				name += tracks.get(i).getName() + " by ";
				for (int j = 0; j < tracks.get(i).getArtists().length; j++) {
					ArtistSimplified art = (ArtistSimplified) Array.get(tracks.get(i).getArtists(), j);
					String artName = art.getName();
					if (tracks.get(i).getArtists().length == 1) {
						name += artName;
					} else if (j == 0 && tracks.get(i).getArtists().length == 2) {
						name += artName + " ";
					} else if ((j + 1) == tracks.get(i).getArtists().length && j != 0) {
						name += "and " + artName;
					} else {
						name += artName + ", ";
					}
				}
				topTracks.add(name);
			}

		} catch (IOException | SpotifyWebApiException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	public static void getArtist_Sync() {
		final String accessToken = spotifyApi.getAccessToken();
		final String id = "31W5EY0aAly4Qieq6OFu6I";
		final GetArtistRequest getArtistRequest = spotifyApi.getArtist(id).build();

		try {
			final Artist artist = getArtistRequest.execute();

			System.out.println("Name: " + artist.getName());
		} catch (IOException | SpotifyWebApiException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	public static void main(String[] args) {
		authorizationCode_Sync();

		// getArtist_Sync();
		getUsersTopArtists_Sync();
		getUsersTopTracks_Sync();
		
		for (int i = 0; i < topArtists.size(); i++) {
			System.out.println("Your #" + (i + 1) + " artist over the last 6 months is: " + topArtists.get(i));
		}
		System.out.println();
		for (int i = 0; i < topTracks.size(); i++) {
			System.out.print("Your #" + (i + 1) + " song over the last 6 months is: ");
			System.out.println(topTracks.get(i));
		}
	}
}
