package fernandocs.weathermap.data.weather

import fernandocs.weathermap.domain.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class WeatherRepositoryImpl(
    private val service: WeatherService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : WeatherRepository {
    override suspend fun getWeather(lat: Double, lon: Double) = withContext(ioDispatcher) {
        service.getCurrentWeather(lat, lon).mapToDomain()
    }
}
