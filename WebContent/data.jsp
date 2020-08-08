<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="infofy.Users.User"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Infofy Data</title>
<link rel="stylesheet" type="text/css" href="style.css">
<script src="functions.js"></script>
</head>
<body>
	<div class="main">
		<div class="title">
			<a href="index.html">Infofy</a>
		</div>
		<%
			User user = (User) request.getAttribute("user");
		if (user == null) {
		%>
		<div class="error">404 Error: No information found</div>
		<button onclick="window.location.href = 'index.html'">Back</button>
		<%
			} else {
		%>

		<div id="currentplaying">
			Currently Playing:
			<%
			if (user.currentlyPlaying != null && !user.currentlyPlaying.equalsIgnoreCase("")) {
		%>
			<%=user.currentlyPlaying%>
			<%
				} else {
			%>
			Nothing
			<%
				}
			%>

		</div>
	</div>
	<div class=container>
		<div id="topSongs">
			<h1>Top Songs</h1>
			<%
				for (int i=0;i<user.topSongs.size();++i){
			%>
			<div class="each">
				<%=i+1 + ". " + user.topSongs.get(i)%>
			</div>
			<%
				}
			%>

		</div>
		<div id="topArtists">
			<h1>Top Artists</h1>

			<%
			for (int i=0;i<user.topArtists.size();++i){
			%>
			<div class="each">
				<%=i+1 + ". " + user.topArtists.get(i)%>
			</div>
			<%
				}
			}
			%>
		</div>
	</div>
	<div style="clear: both"></div>

</body>
</html>