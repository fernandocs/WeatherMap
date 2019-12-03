package fernandocs.weathermap.data.bookmark.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import fernandocs.weathermap.domain.bookmark.Bookmark

@Entity(tableName = "bookmarks")
data class BookmarkLocal(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int?,
    @ColumnInfo(name = "latitude") val latitude: Double,
    @ColumnInfo(name = "longitude") val longitude: Double,
    @ColumnInfo(name = "name") val name: String
) {
    internal fun mapToDomain() = Bookmark(
        id = this.id,
        latitude = this.latitude,
        longitude = this.longitude,
        name = this.name
    )
    companion object {
        fun transform(domainBookmark: Bookmark) = BookmarkLocal(
            id = domainBookmark.id,
            latitude = domainBookmark.latitude,
            longitude = domainBookmark.longitude,
            name = domainBookmark.name
        )
    }

}
