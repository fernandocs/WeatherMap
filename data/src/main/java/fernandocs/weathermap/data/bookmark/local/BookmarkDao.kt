package fernandocs.weathermap.data.bookmark.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import fernandocs.weathermap.data.bookmark.dto.BookmarkLocal

@Dao
interface BookmarkDao {
    @get:Query("SELECT * FROM bookmarks")
    val all: List<BookmarkLocal>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(bookmark: BookmarkLocal)

    @Delete
    fun delete(bookmark: BookmarkLocal)
}
