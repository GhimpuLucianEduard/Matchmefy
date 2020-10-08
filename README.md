# Matchmefy 

<img src="https://github.com/GhimpuLucianEduard/Matchmefy/blob/master/google-play-graphics/Graphic.png">

<img src="https://github.com/GhimpuLucianEduard/Matchmefy/blob/master/google-play-graphics/Search.png" width="300"> <img src="https://github.com/GhimpuLucianEduard/Matchmefy/blob/master/google-play-graphics/Match.png" width="300"> <img src="https://github.com/GhimpuLucianEduard/Matchmefy/blob/master/google-play-graphics/Artists.png" width="300"> <img src="https://github.com/GhimpuLucianEduard/Matchmefy/blob/master/google-play-graphics/Tracks.png" width="300"> <img src="https://github.com/GhimpuLucianEduard/Matchmefy/blob/master/google-play-graphics/Genres.png" width="300"> <img src="https://github.com/GhimpuLucianEduard/Matchmefy/blob/master/google-play-graphics/Playlist.png" width="300">

Android mobile app used to showcase the usage of the latest Android Jetpack libraries, coroutines, and the latest best practices in android.

The app retrieves data about the user from the Spotify API, stores it in a secure database.
Then the user can search for friends or random people and get a "match score" which calculates how compatible their music tastes are. In the end, the user gets a Spotify playlist with songs from his match.

:heart: [Website](https://www.matchmefy.com)

## APIs 

### 1. [Spotify Web API](https://developer.spotify.com/documentation/web-api/)

Used to fetch the user profile and their top 50 artists and tracks

### 2. [Matchmefy API](https://github.com/GhimpuLucianEduard/matchmefy-api) 

_Note: Matchmefy Api repository has been made private for security reasons_

Custom REST API developt in **Node.js**, used to store user data in a **Mongo DB** and handles the "logic" to get the match summary for 2 users.


## Architecture

The app is built with the latest **Android Jetpack** :rocket: libraries using the **MVVM** :triangular_ruler: architectural pattern, **data binding** and **LiveData**

Other Android libraries used:
 
1. `Jetpack Navigation` -> MVVM navigation, all handled in the view models :rocket:
2. `Coroutines` -> used to handle http requests and other async calls :sunglasses:
3. `Koin` -> DI :pushpin:
4. `Retrofit` + `OkHttp` -> staples libraries used to handle http calls to REST apis :cloud:
5. `AppAuth` -> used to handle `OAuth2` using `Authorization Code Flow + PKCE` :police_car:
6. `Timber` -> Better Logging :chart_with_upwards_trend:
7. `Firebase` -> Analytics and Crashalytics :boom:

Special Thanks to [Undarw](https://undraw.co/search) for the amazing free illustrations :pray:

# FAQ

Q: Will Matchmefy be available on iOS and Web?
A: It is planned, but not a priority. If you are an iOS dev. or web dev. and looking to extend this project, contact me and I can provided deisgn specs, api, db access and all.

Q: I can match with myself, is this intended?
A: Yes! You can match with yourself but you will not be able to create a playlist.

