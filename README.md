# Infofy - Spotify REST API Web Server

- Access Spotify information such as top songs, top artists, and the currently playing song.  
- Program pulls constantly so information such as currently playing song changes instantaneously.  
- Runs using Apache Tomcat on port 8080
- API Keys are outdated and only placeholders


## Screenshots:


![Home](https://i.imgur.com/gVwGztD.png)

![Example_1](https://i.imgur.com/qi7UxZB.png)


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

