package fernandocs.weathermap.domain.bookmark

import javax.inject.Inject

open class GetBookmarks @Inject constructor(
    private val repository: BookmarkRepository
) {
    suspend operator fun invoke() = repository.getBookmarks()
}
