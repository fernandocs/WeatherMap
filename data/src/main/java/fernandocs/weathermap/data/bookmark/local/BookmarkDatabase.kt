package fernandocs.weathermap.data.bookmark.local

import androidx.room.Database
import androidx.room.RoomDatabase
import fernandocs.weathermap.data.bookmark.dto.BookmarkLocal

@Database(entities = [BookmarkLocal::class], version = 1)
abstract class BookmarkDatabase : RoomDatabase() {
    abstract val bookmarkDao: BookmarkDao
}
