# WeatherMap

This is an app to present the weather of the location in the center of the map.
To make this app usual you should to add the correct keys for the google maps(**google_maps_api.xml**) and openweathermap(**WeatherService.kt**)

- You can save the location as a bookmark tapping the icon on top
- All the bookmarks that you have saved can be access from the bar on the bottom of the screen.
- The weather of the bookmark can be access tapping on the bookmark itself.
- The help screen can be access tapping on the question icon on the top-right of the screen

<img src="https://github.com/fernandocs/WeatherMap/raw/master/prints/1.png" alt="layers"/>
<img src="https://github.com/fernandocs/WeatherMap/raw/master/prints/2.png" alt="layers"/>

## Architecture
MVI and Clean

## Libraries
- **Dagger2** - Dependency injection
- **Retrofit** - Networking
- **Mockk** - Mock and unit testing (Missing)
- **Gson** - Json parsing
- **Glide** - Image loading
- **Android Components** - in general app(ViewModel, Navigation)
- **Coroutines** - Async
