package infofy;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/api/")
public class UserApplication extends ResourceConfig {

    public UserApplication() {
        packages("user_song");
    }
	
}
