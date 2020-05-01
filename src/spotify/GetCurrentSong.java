package spotify;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.concurrent.Callable;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.miscellaneous.CurrentlyPlayingContext;
import com.wrapper.spotify.model_objects.specification.ArtistSimplified;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.requests.data.player.GetInformationAboutUsersCurrentPlaybackRequest;

public class GetCurrentSong implements Callable<String>{

	private SpotifyApi spotifyApi;
	public GetCurrentSong(SpotifyApi spotifyApi) {
		this.spotifyApi = spotifyApi;
	}
	
	@Override
	public String call() throws Exception {
		final GetInformationAboutUsersCurrentPlaybackRequest getInformationAboutUsersCurrentPlaybackRequest = spotifyApi
				.getInformationAboutUsersCurrentPlayback().build();
		Track track = null;

		try {
			final CurrentlyPlayingContext currentlyPlayingContext = getInformationAboutUsersCurrentPlaybackRequest
					.execute();
			if (currentlyPlayingContext.getIs_playing()) {
				track = currentlyPlayingContext.getItem();
			}

			if (track != null) {
				String name = "";
				name += track.getName() + " by ";
				for (int j = 0; j < track.getArtists().length; j++) {
					ArtistSimplified art = (ArtistSimplified) Array.get(track.getArtists(), j);
					String artName = art.getName();
					if (track.getArtists().length == 1) {
						name += artName;
					} else if (j == 0 && track.getArtists().length == 2) {
						name += artName + " ";
					} else if ((j + 1) == track.getArtists().length && j != 0) {
						name += "and " + artName;
					} else {
						name += artName + ", ";
					}
				}
				System.out.println("Current Song: " + name);
				return name;
			}
		} catch (IOException | SpotifyWebApiException | NullPointerException e) {
			System.out.println("Error in getCurrentPlayback: " + e.getMessage());
		}
		return "";

	}
}
