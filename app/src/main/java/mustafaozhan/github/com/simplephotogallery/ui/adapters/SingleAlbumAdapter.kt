package mustafaozhan.github.com.simplephotogallery.ui.adapters

import android.graphics.Bitmap
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.RequestBuilder
import kotlinx.android.synthetic.main.list_layout.view.*
import mustafaozhan.github.com.simplephotogallery.R
import mustafaozhan.github.com.simplephotogallery.ui.activities.AlbumActivity

/**
 * Created by Mustafa Ozhan on 12/31/17 at 8:54 PM on Arch Linux.
 */
class SingleAlbumAdapter(private val albumList: MutableList<String>, private val glide: RequestBuilder<Bitmap>, private val inOnItemClick: AlbumActivity) : RecyclerView.Adapter<SingleAlbumAdapter.ViewHolder>() {


    override fun onViewRecycled(holder: ViewHolder?) {
        if (holder != null) {
        }
        super.onViewRecycled(holder)
    }

    override fun getItemCount(): Int {
        return albumList.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindItems(albumList[position], glide, inOnItemClick)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_single_album_layout, parent, false)
        return ViewHolder(v)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(albumList: String, glide: RequestBuilder<Bitmap>, inOnItemClick: AlbumFoldersAdapter.IOnItemClick) {

            glide.load(albumList).thumbnail(0.4f)
                    .into(itemView.thumbnail)

            itemView.setOnClickListener { inOnItemClick.onItemClick(albumList, false) }
        }
    }
}