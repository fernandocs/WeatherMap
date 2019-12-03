package fernandocs.weathermap.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fernandocs.weathermap.SingleLiveEvent
import fernandocs.weathermap.domain.GetWeather
import fernandocs.weathermap.domain.bookmark.Bookmark
import fernandocs.weathermap.domain.bookmark.DeleteBookmark
import fernandocs.weathermap.domain.bookmark.GetBookmarks
import fernandocs.weathermap.domain.bookmark.SaveBookmark
import kotlinx.coroutines.launch
import javax.inject.Inject

open class MainViewModel @Inject constructor(
    private val getWeather: GetWeather,
    private val getBookmarks: GetBookmarks,
    private val saveBookmark: SaveBookmark,
    private val deleteBookmark: DeleteBookmark,
    private val weatherViewStateMapper: WeatherViewStateMapper,
    private val bookmarkViewStateMapper: BookmarkViewStateMapper
) : ViewModel() {

    private val _navigate = SingleLiveEvent<MainNavigationCommand>()
    val navigate: LiveData<MainNavigationCommand>
        get() = _navigate

    private val _viewState = MutableLiveData(MainViewState())
    val viewState: LiveData<MainViewState>
        get() = _viewState

    private fun updateViewState(reducer: (MainViewState) -> MainViewState) {
        _viewState.value?.run { _viewState.value = reducer(this) }
    }

    fun start() {
        if (!_viewState.value?.isInitial!!) return
        viewModelScope.launch {
            loadWeather(52.371370, 4.928311)
            loadBookmarks()
            updateCurrentLocationIsABookmark()
        }
    }

    fun handleIntent(intent: ComparisonIntent) = viewModelScope.launch {
        when (intent) {
            is ComparisonIntent.LoadWeather -> {
                loadWeather(intent.lat, intent.lon)
                updateCurrentLocationIsABookmark()
            }
            is ComparisonIntent.HelpButtonTapped -> _navigate.setValue(MainNavigationCommand.ToHelp)
            is ComparisonIntent.SaveBookmark -> {
                _viewState.value?.weatherViewState?.let { weather ->
                    val bookmark = _viewState.value?.bookmarks?.find { it.lat == weather.lat && it.lon == weather.lon }
                    bookmark?.let {
                        deleteBookmark(bookmark.id, bookmark.lat, bookmark.lon, bookmark.name)
                        loadBookmarks()
                    } ?: saveBookmark(latitude = weather.lat, longitude = weather.lon, name = weather.cityName)
                    loadBookmarks()
                    updateCurrentLocationIsABookmark()
                }
            }
            is ComparisonIntent.DeleteBookmark -> {
                val bookmark = _viewState.value?.bookmarks?.find { it.id == intent.id }
                bookmark?.let {
                    deleteBookmark(it.id, it.lat, it.lon, it.name)
                    loadBookmarks()
                }
            }
            is ComparisonIntent.LoadBookmarkWeather -> {
                val bookmark = _viewState.value?.bookmarks?.find { it.id == intent.id }
                bookmark?.let {
                    loadWeather(it.lat, it.lon)
                    updateCurrentLocationIsABookmark()
                }
            }
        }
    }

    private suspend fun deleteBookmark(id: Int, latitude: Double, longitude: Double, name: String) {
        try {
            deleteBookmark(
                Bookmark(
                    id = id,
                    latitude = latitude,
                    longitude = longitude,
                    name = name
                )
            )
        } catch (t: Throwable) {
            // show error
        }
    }

    private suspend fun saveBookmark(latitude: Double, longitude: Double, name: String) {
        try {
            saveBookmark(Bookmark(latitude = latitude, longitude = longitude, name = name))
        } catch (t: Throwable) {
            // show error
        }
    }

    private suspend fun loadBookmarks() {
        try {
            val bookmarks = getBookmarks()

            updateViewState {
                it.copy(bookmarks = bookmarks.map(bookmarkViewStateMapper::transform))
            }
        } catch (t: Throwable) {
            // show error
        }
    }

    private suspend fun loadWeather(lat: Double, lon: Double) {
        try {
            val result = getWeather(lat, lon)
            updateViewState {
                it.copy(weatherViewState = weatherViewStateMapper.transform(result))
            }
        } catch (t: Throwable) {
            // show error
        }
    }

    private fun updateCurrentLocationIsABookmark() {
        val isBookmark = _viewState.value?.bookmarks?.find {
            it.lat == _viewState.value?.weatherViewState?.lat && it.lon == _viewState.value?.weatherViewState?.lon
        }
        updateViewState { it.copy(weatherViewState = it.weatherViewState?.copy(isBookmark = isBookmark != null)) }
    }
}

sealed class MainNavigationCommand {
    object ToHelp : MainNavigationCommand()
}

sealed class ComparisonIntent {
    data class LoadWeather(val lat: Double, val lon: Double) : ComparisonIntent()
    data class LoadBookmarkWeather(val id: Int) : ComparisonIntent()
    object SaveBookmark : ComparisonIntent()
    data class DeleteBookmark(val id: Int) : ComparisonIntent()
    object HelpButtonTapped : ComparisonIntent()
}