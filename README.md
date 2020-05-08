# spotifyAPI

In main.java, run the link on a web browser. Then get the code from the link and copy and paste it to the String code. 
https://accounts.spotify.com/authorize?response_type=code&client_id=2d77bc05e0f841679c7455f113130e4f&scope=user-top-read user-read-playback-state&redirect_uri=https://www.spotify.com/us/

### REST Web Server

- Get API information

http://localhost:8080/spotifyAPI/token?code={access_token}

- Show all users

http://localhost:8080/spotifyAPI/map

- Create users

http://localhost:8080/spotifyAPI/create?user={username}&pw={password}

- Show user information

http://localhost:8080/spotifyAPI/{username}

- Gets data from Spotify for that user

http://localhost:8080/spotifyAPI/{username}/token?code={access_token}

- Get top songs

http://localhost:8080/spotifyAPI/{username}/topSongs

- Get top albums

http://localhost:8080/spotifyAPI/{username}/topAlbums
