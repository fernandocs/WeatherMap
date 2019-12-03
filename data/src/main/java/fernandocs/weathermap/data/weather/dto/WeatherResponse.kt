package fernandocs.weathermap.data.weather.dto

import com.google.gson.annotations.SerializedName
import fernandocs.weathermap.domain.Weather

internal data class WeatherResponse(
    val name: String?,
    val coord: CoordResponse?,
    val weather: List<WeatherDataResponse>?,
    val main: MainResponse,
    val wind: WindResponse?,
    val rain: RainResponse?,
    val sys: SysResponse?
) {
    fun mapToDomain() = Weather(
        latitude = this.coord?.lat.let { it } ?: 0.0,
        longitude = this.coord?.lon?.let { it } ?: 0.0,
        name = this.name?.let { n ->
            n.plus(sys?.let {
                it.country?.let { country -> " - $country" } ?: "-"
            })
        } ?: "-",
        humidity = this.main.humidity,
        wind = this.wind?.speed,
        rain = this.rain?.oneH,
        temperature = this.main.temp,
        temperatureIconUrl = this.weather?.firstOrNull()?.let { "https://openweathermap.org/img/wn/${it.icon}@2x.png" }
    )
}

internal data class CoordResponse(
    val lat: Double,
    val lon: Double
)

internal data class WeatherDataResponse(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

internal data class MainResponse(
    val temp: Double,
    val humidity: Double
)

internal data class WindResponse(
    val speed: Double
)

internal data class RainResponse(
    @SerializedName("1h") val oneH: Double,
    @SerializedName("3h") val threeH: Double?
)

internal data class SysResponse(
    val country: String?
)
