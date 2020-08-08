# Searchify - Spotify REST API Web Server

Access Spotify information such as top songs, top artists, and the currently playing song. 
Login

Home Screen:

![Home](https://imgur.com/a/Vxepaca)
![Token_Area](https://imgur.com/a/j4FoQOw)


### REST API Endpoints

Link that authorizes information: "https://accounts.spotify.com/authorize?response_type=code&client_id=2d77bc05e0f841679c7455f113130e4f&scope=user-top-read-user-read-playback-state&redirect_uri=https://www.spotify.com/us/"

- Get Spotify API information(JSON)

http://localhost:8080/spotifyAPI/api/token?code={access_token}

#### Depreciated Endpoints

- Show all users

http://localhost:8080/spotifyAPI/api/map

- Create users

http://localhost:8080/spotifyAPI/api/create?user={username}&pw={password}

- Show user information

http://localhost:8080/spotifyAPI/api/{username}

- Gets data from Spotify for that user

http://localhost:8080/spotifyAPI/api/{username}/token?code={access_token}

- Get top songs

http://localhost:8080/spotifyAPI/api/{username}/topSongs

- Get top albums

http://localhost:8080/spotifyAPI/api/{username}/topAlbums
