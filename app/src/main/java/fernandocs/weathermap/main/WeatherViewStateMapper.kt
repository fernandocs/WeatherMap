package fernandocs.weathermap.main

import fernandocs.weathermap.domain.Weather
import javax.inject.Inject
import kotlin.math.roundToInt

open class WeatherViewStateMapper @Inject constructor() {
    open fun transform(weather: Weather) = WeatherViewState(
        lat = weather.latitude,
        lon = weather.longitude,
        cityName = weather.name,
        temperature = weather.temperature?.roundToInt().toString(),
        temperatureIconUrl = weather.temperatureIconUrl,
        humidity = weather.humidity.toString(),
        wind = weather.wind.toString(),
        rain = weather.rain?.let { it.roundToInt().toString() }
    )
}