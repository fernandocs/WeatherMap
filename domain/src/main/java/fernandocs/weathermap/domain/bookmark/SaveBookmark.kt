package fernandocs.weathermap.domain.bookmark

import javax.inject.Inject

open class SaveBookmark @Inject constructor(
    private val repository: BookmarkRepository
) {
    suspend operator fun invoke(bookmark: Bookmark) = repository.save(bookmark)
}
