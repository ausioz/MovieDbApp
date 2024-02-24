# MovieDBApp

1. Show List of Genres from https://api.themoviedb.org
(https://github.com/ausioz/MovieDbApp/assets/25804478/5e444ccb-d1f2-4b19-878e-68b63233d007)

2. Show a List of Movies by Genre chosen
(https://github.com/ausioz/MovieDbApp/assets/25804478/5299e1a9-0644-4c51-b529-71e727b4c178)

3. Show Movie Detail: Trailer, Poster, Genre, Rating, Overview, and User Reviews
(https://github.com/ausioz/MovieDbApp/assets/25804478/106b26c5-7a3d-42f3-b64a-c0724b36961c)

4. This App uses MVVM architecture, Android YouTube player library, and Paging 3 to create an endless scroll of movies and reviews


Positive cases :
1. Using OkHttp builder with different modes for debug and release app, and saving apikey to the build config to ensure the app's security
2. Using chain header from retrofit to make apikey use more convenient
3. Using Paging 3 to make endless scroll, and custom view dialog fragment to make loading

Negative cases :
1. Finding a YouTube "Official Trailer" from a remote data source is still too simple and needs more research cases
2. Layout is still focused on the phone (non-tablet) and portrait mode
3. The app still uses simple style coloring and theme
