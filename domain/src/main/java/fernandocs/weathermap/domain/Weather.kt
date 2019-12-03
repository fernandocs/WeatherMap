package fernandocs.weathermap.domain

data class Weather(
    val latitude: Double,
    val longitude: Double,
    val name: String,
    val humidity: Double? = null,
    val wind: Double? = null,
    val rain: Double? = null,
    val temperature: Double? = null,
    val temperatureIconUrl: String? = null
)
