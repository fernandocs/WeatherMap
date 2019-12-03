package fernandocs.weathermap.data.weather.di

import dagger.Module
import dagger.Provides
import fernandocs.weathermap.data.ApiClientFactory
import fernandocs.weathermap.data.weather.WeatherRepositoryImpl
import fernandocs.weathermap.data.weather.WeatherService
import fernandocs.weathermap.domain.WeatherRepository

@Module
internal class WeatherModule {
    @Provides
    fun provideWeatherRepository(service: WeatherService): WeatherRepository {
        return WeatherRepositoryImpl(service)
    }

    @Provides
    internal fun provideWeatherService(apiClientFactory: ApiClientFactory): WeatherService {
        return apiClientFactory.retrofit.create(WeatherService::class.java)
    }
}
