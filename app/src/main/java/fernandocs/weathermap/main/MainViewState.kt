package fernandocs.weathermap.main

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MainViewState(
    val isInitial: Boolean = true,
    val weatherViewState: WeatherViewState? = null,
    val bookmarks: List<BookmarkViewState> = emptyList()
) : Parcelable

@Parcelize
data class WeatherViewState(
    val lat: Double,
    val lon: Double,
    val cityName: String,
    val temperature: String,
    val temperatureIconUrl: String?,
    val humidity: String,
    val wind: String,
    val rain: String?,
    val isBookmark: Boolean = false
) : Parcelable

@Parcelize
data class BookmarkViewState(
    val id: Int,
    val lat: Double,
    val lon: Double,
    val name: String
) : Parcelable
