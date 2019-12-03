package fernandocs.weathermap

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fernandocs.weathermap.main.BookmarkViewState
import kotlinx.android.synthetic.main.item_bookmark.view.*

class BookmarkAdapter(
    private val listener: BookmarkOnClickListener
) : RecyclerView.Adapter<BookmarkAdapter.ViewHolder>() {
    private val bookmarks = mutableListOf<BookmarkViewState>()

    fun update(newList: List<BookmarkViewState>) {
        bookmarks.clear()
        bookmarks.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_bookmark,
            parent,
            false
        )
    )

    override fun getItemCount() = bookmarks.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bookmarkTitle.text = bookmarks[position].name
        holder.bookmarkTitle.setOnClickListener { listener.onItemClick(bookmarks[position].id) }
        holder.deleteIcon.setOnClickListener { listener.onDeleteClick(bookmarks[position].id) }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val bookmarkTitle = view.bookmarkTitle!!
        val deleteIcon = view.deleteIcon!!
    }
}

interface BookmarkOnClickListener {
    fun onItemClick(id: Int)
    fun onDeleteClick(id: Int)
}