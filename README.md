# spotifyAPI

In main.java, run the link on a web browser. Then get the code from the link and copy and paste it to the String code. 

### REST Web Server

- Show all users

http://localhost:8080/spotifyAPI/map

- Create users

http://localhost:8080/spotifyAPI/create?user={username}&pw={password}

- Show user information

http://localhost:8080/spotifyAPI/{username}

- Add access token to user
- Gets data from Spotify

http://localhost:8080/spotifyAPI/{username}/token?code={access_token}

- Get top songs

http://localhost:8080/spotifyAPI/{username}/topSongs

- Get top albums

http://localhost:8080/spotifyAPI/{username}/topAlbums
