package spotify;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Artist;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.requests.data.personalization.simplified.GetUsersTopArtistsRequest;

public class GetTopArtists implements Callable<ArrayList<String>> {
	private static ArrayList<String> topArtists;
	private SpotifyApi spotifyApi;

	public GetTopArtists(SpotifyApi spotifyApi) {
		this.spotifyApi = spotifyApi;
		topArtists = new ArrayList<String>();
	}

	@Override
	public ArrayList<String> call() throws SpotifyWebApiException, IOException{
		topArtists.clear();
		final String accessToken = spotifyApi.getAccessToken();

		final SpotifyApi spotifyApi = new SpotifyApi.Builder().setAccessToken(accessToken).build();
		final GetUsersTopArtistsRequest getUsersTopArtistsRequest = spotifyApi.getUsersTopArtists()
				.time_range("short_term").build();

		final Paging<Artist> artistPaging = getUsersTopArtistsRequest.execute();

		for (int i = 0; i < 5; i++) {
			try {

				Artist artist = (Artist) Array.get(artistPaging.getItems(), i);
				String artistName = artist.getName();
				Thread.sleep(100);
				
				System.out.println("Top Artist " + i + ": " + artistName);
				topArtists.add(artistName);
			} catch (NullPointerException | ArrayIndexOutOfBoundsException | InterruptedException e) {
				System.out.println("Error in getTopArtists: " + e.getMessage());
			} 
		}
		return topArtists;
	}

}
