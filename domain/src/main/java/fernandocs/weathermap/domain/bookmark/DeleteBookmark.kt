package fernandocs.weathermap.domain.bookmark

import javax.inject.Inject

open class DeleteBookmark @Inject constructor(
    private val repository: BookmarkRepository
) {
    suspend operator fun invoke(bookmark: Bookmark) = repository.delete(bookmark)
}
