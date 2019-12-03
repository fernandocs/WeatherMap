package fernandocs.weathermap.main

import fernandocs.weathermap.domain.bookmark.Bookmark
import javax.inject.Inject

open class BookmarkViewStateMapper @Inject constructor() {
    open fun transform(bookmark: Bookmark) = BookmarkViewState(
        id = bookmark.id!!,
        lat = bookmark.latitude,
        lon = bookmark.longitude,
        name = bookmark.name
    )
}