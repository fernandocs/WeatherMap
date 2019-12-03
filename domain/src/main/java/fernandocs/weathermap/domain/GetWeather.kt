package fernandocs.weathermap.domain

import javax.inject.Inject

open class GetWeather @Inject constructor(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(lat: Double, lon: Double) = repository.getWeather(lat, lon)
}
