package fernandocs.weathermap.domain

interface WeatherRepository {
    suspend fun getWeather(lat: Double, lon: Double): Weather
}