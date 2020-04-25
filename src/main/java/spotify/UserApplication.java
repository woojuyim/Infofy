package spotify;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/")
public class UserApplication extends ResourceConfig {

    public UserApplication() {
        packages("user_song");
    }
	
}
