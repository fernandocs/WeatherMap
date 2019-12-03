package fernandocs.weathermap.data.bookmark.di

import dagger.Module
import dagger.Provides
import fernandocs.weathermap.data.bookmark.BookmarkRepositoryImpl
import fernandocs.weathermap.data.bookmark.local.BookmarkDao
import fernandocs.weathermap.data.bookmark.local.BookmarkDatabase
import fernandocs.weathermap.domain.bookmark.BookmarkRepository

@Module
internal class BookmarkModule {
    @Provides
    fun provideBookmarkRepository(dao: BookmarkDao): BookmarkRepository {
        return BookmarkRepositoryImpl(dao)
    }

    @Provides
    fun providesBookmarkDao(database: BookmarkDatabase): BookmarkDao {
        return database.bookmarkDao
    }
}
