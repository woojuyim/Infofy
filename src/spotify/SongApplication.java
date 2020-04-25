package spotify;


import javax.ws.rs.*;
import org.glassfish.jersey.server.ResourceConfig;

@Path("/")
public class SongApplication extends ResourceConfig {

    public SongApplication() {
        packages("user_song");
    }
	
}
