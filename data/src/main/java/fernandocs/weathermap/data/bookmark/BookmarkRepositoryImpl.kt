package fernandocs.weathermap.data.bookmark

import fernandocs.weathermap.data.bookmark.dto.BookmarkLocal
import fernandocs.weathermap.data.bookmark.local.BookmarkDao
import fernandocs.weathermap.domain.bookmark.Bookmark
import fernandocs.weathermap.domain.bookmark.BookmarkRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class BookmarkRepositoryImpl(
    private val dao: BookmarkDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : BookmarkRepository {

    override suspend fun getBookmarks() = withContext(ioDispatcher) {
        dao.all.map { it.mapToDomain() }
    }

    override suspend fun save(bookmark: Bookmark) {
        withContext(ioDispatcher) { dao.insert(BookmarkLocal.transform(bookmark)) }
    }

    override suspend fun delete(bookmark: Bookmark) {
        withContext(ioDispatcher) { dao.delete(BookmarkLocal.transform(bookmark)) }
    }
}
