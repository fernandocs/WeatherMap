package fernandocs.weathermap.domain.bookmark

interface BookmarkRepository {
    suspend fun getBookmarks(): List<Bookmark>
    suspend fun save(bookmark: Bookmark)
    suspend fun delete(bookmark: Bookmark)
}