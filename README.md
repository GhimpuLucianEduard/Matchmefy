# Matchmefy

Android mobile app used to showcase the usage of the latest Android Jetpack libraries, coroutines, and the latest best practices in android.

The app retrieves data about the user from the Spotify API, stores it in a secure database.
Then the user can search for friends or strangers and get a "match score" which calculates how compatible their music tastes are. In the end, the user gets a Spotify playlist with songs from his match.

## APIs 

### 1. [Spotify Web API](https://developer.spotify.com/documentation/web-api/)

Used to fetch the user profile and their top 50 artists and tracks

### 2. [Matchmefy API used to store the data](https://github.com/GhimpuLucianEduard/matchmefy-api)

Custom REST API developt in **Node.js**, used to store user data in a **Mongo DB** and handles the "logic" to get the match summary for 2 users.


## Architecture

The app is built with the latest **Android Jetpack** libraries using the **MVVM** architectural pattern, **data binding** and **LiveData**

Other Android libraries used:

1. `Jetpack Navigation` -> MVVM navigation, all handled in the view models
2. `Coroutines` -> used to handle http requests and other async calls
3. `Koin` -> DI
4. `Retrofit` + `OkHttp` -> staples libraries used to handle http calls to REST apis
5. `AppAuth` -> used to handle `OAuth2` using `Authorization Code Flow + PKCE`


## Adobe XD images

<img src="https://github.com/GhimpuLucianEduard/Matchmefy/blob/master/images/signin.png" width="250"> <img src="https://github.com/GhimpuLucianEduard/Matchmefy/blob/master/images/welcome.png" width="250">

<img src="https://github.com/GhimpuLucianEduard/Matchmefy/blob/master/images/serch.png" width="250"> <img src="https://github.com/GhimpuLucianEduard/Matchmefy/blob/master/images/empty.png" width="250"> <img src="https://github.com/GhimpuLucianEduard/Matchmefy/blob/master/images/match.png" width="250"> 

<img src="https://github.com/GhimpuLucianEduard/Matchmefy/blob/master/images/match80.png" width="250"> <img src="https://github.com/GhimpuLucianEduard/Matchmefy/blob/master/images/match60.png" width="250"> <img src="https://github.com/GhimpuLucianEduard/Matchmefy/blob/master/images/match40.png" width="250"> <img src="https://github.com/GhimpuLucianEduard/Matchmefy/blob/master/images/match20.png" width="250"> <img src="https://github.com/GhimpuLucianEduard/Matchmefy/blob/master/images/match0.png" width="250"> 

<img src="https://github.com/GhimpuLucianEduard/Matchmefy/blob/master/images/matchingArtists.png" width="250"> <img src="https://github.com/GhimpuLucianEduard/Matchmefy/blob/master/images/matchingTracks.png" width="250"> <img src="https://github.com/GhimpuLucianEduard/Matchmefy/blob/master/images/matchingGenres.png" width="250"> 

<img src="https://github.com/GhimpuLucianEduard/Matchmefy/blob/master/images/createPlaylist.png" width="250"> <img src="https://github.com/GhimpuLucianEduard/Matchmefy/blob/master/images/createPlaylistConfirmation.png" width="250"> <img src="https://github.com/GhimpuLucianEduard/Matchmefy/blob/master/images/playlistCreated.png" width="250">

<img src="https://github.com/GhimpuLucianEduard/Matchmefy/blob/master/images/settings.png" width="250"> 
