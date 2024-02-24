# MovieDBApp

1. Show the List of Genres from https://api.themoviedb.org
   
<img src="https://github.com/ausioz/MovieDbApp/assets/25804478/e4d86a29-ea38-4608-9836-78c0c3d71719" height="500">

3. Show a List of Movies by Genre chosen

<img src="https://github.com/ausioz/MovieDbApp/assets/25804478/8649d915-a98c-4a0c-99ce-d1e677da7afb" height="500">

3. Show Movie Details: Trailer, Poster, Genre, Rating, Overview, and User Reviews

<img src="https://github.com/ausioz/MovieDbApp/assets/25804478/94616b95-5204-4687-97ad-75658bbe48d4" height="500">

4. This App uses MVVM architecture, Android YouTube player library, and Paging 3 to create an endless scroll of movies and reviews


Positive cases :
1. Using OkHttp builder with different modes for debug and release app, and saving apikey to the build config to ensure the app's security
2. Using chain header from retrofit to make apikey use more convenient
3. Using Paging 3 to make endless scroll, and custom view dialog fragment to make loading

Negative cases :
1. Finding a YouTube "Official Trailer" from a remote data source is still too simple and needs more research cases
2. Layout is still focused on the phone (non-tablet) and portrait mode
3. The app still uses simple style coloring and theme
