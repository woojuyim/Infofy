# Infofy - Spotify REST API Web Server

Access Spotify information such as top songs, top artists, and the currently playing song.  
Runs using Apache Tomcat.  
Front-end only works on port 8080


## Screenshots:


![Home](https://i.imgur.com/195QuBf.png)

![Token_Area](https://i.imgur.com/xg8YiRX.png)


## REST API Endpoints 


- Get Spotify API information(JSON)

/infofy/api/token?code={access_token}

#### Depreciated Endpoints


- Show all users

/infofy/api/map

- Create users

/infofy/api/create?user={username}&pw={password}

- Show user information

/infofy/api/{username}

- Gets data from Spotify for that user

/infofy/api/{username}/token?code={access_token}

- Get top songs

/infofy/api/{username}/topSongs

- Get top albums

/infofy/api/{username}/topAlbums

Link that authorizes information: https://accounts.spotify.com/authorize?response_type=code&client_id=75fbbc3b3d8b4bc2ba1e1c197221602e&scope=user-top-read%20user-read-playback-state&redirect_uri=http://localhost:8080/infofy/data
