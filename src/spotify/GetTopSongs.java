package spotify;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.ArtistSimplified;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.requests.data.personalization.simplified.GetUsersTopTracksRequest;

public class GetTopSongs implements Callable<ArrayList<String>> {

	private static ArrayList<String> topTracks;
	private SpotifyApi spotifyApi;

	public GetTopSongs(SpotifyApi spotifyApi) {
		this.spotifyApi = spotifyApi;
		topTracks = new ArrayList<String>();
	}

	@Override
	public ArrayList<String> call() throws IOException, SpotifyWebApiException  {
		topTracks.clear();
		final GetUsersTopTracksRequest getUsersTopTracksRequest = spotifyApi.getUsersTopTracks()
				.time_range("short_term").build();
		final String accessToken = spotifyApi.getAccessToken();

		final SpotifyApi spotifyApi = new SpotifyApi.Builder().setAccessToken(accessToken).build();

		final Paging<Track> trackPaging = getUsersTopTracksRequest.execute();

		ArrayList<Track> tracks = new ArrayList<Track>();
		for (int i = 0; i < 10; i++) {
			Track track = (Track) Array.get(trackPaging.getItems(), i);
			tracks.add(track);
		}
		for (int i = 0; i < tracks.size(); i++) {
			try {
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
				System.out.println("Top Song " + i + ": " + name);
				Thread.sleep(100);
				topTracks.add(name);
			} catch (NullPointerException | ArrayIndexOutOfBoundsException | InterruptedException e) {
				System.out.println("Error in getTopSongs: " + e.getMessage());
			} 
		}
		return topTracks;
	}

}
