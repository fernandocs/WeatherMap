package fernandocs.weathermap.data.weather

import fernandocs.weathermap.data.weather.dto.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

internal interface WeatherService {
    @GET("/data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appId: String = "ADD_YOUR_KEY_HERE",
        @Query("units") units: String = "metric"
    ) : WeatherResponse
}
