package spotify;

import javax.ws.rs.*;

@Path("/")
public class SongService {
	
	@GET
	@Path("/song")
	@Produces("application/json")
	public String getSong() {
		String pattern = "{ \"title\": \"%s\", \"artist\": \"%s\", \"album\": \"%s\"}";
		return String.format(pattern, Song.title, Song.artist, Song.album);
	}
	
//	@PUT
//	@Path("/song")
//	@Produces("application/json")
//	public String setSong(@QueryParam("title") String title,
//							@QueryParam("artist") String artist,
//							@QueryParam("album") String album) {
//		Song.title = title;
//		Song.artist = artist;
//		Song.album = album;
//		String pattern = "{ \"title\": \"%s\", \"artist\": \"%s\", \"album\": \"%s\"}";
//		return String.format(pattern, Song.title, Song.artist, Song.album);
//	}
	
//	@GET
//	@Path("/song/title")
//	@Produces("text/plain")
//	public String getTitle() {
//		return Song.title;
//	}
//	
//	@POST @Path("/song/title")
//	@Produces("text/plain")
//	public void setTitle(String title) {
//		Song.title = title;
//	}
//	
//	@GET
//	@Path("/song/artist")
//	@Produces("text/plain")
//	public String getArtist() {
//		return Song.artist;
//	}
//	@POST
//	@Path("/song/artist")
//	@Produces("text/plain")
//	public void setArtist(String artist) {
//		Song.artist = artist;
//	}
//	
//	@GET
//	@Path("/song/album")
//	@Produces("text/plain")
//	public String getAlbum() {
//		return Song.album;
//	}
//	@POST
//	@Path("/song/album")
//	@Produces("text/plain")
//	public void setAlbum(String album) {
//		Song.album = album;
//	}
	
	
}
