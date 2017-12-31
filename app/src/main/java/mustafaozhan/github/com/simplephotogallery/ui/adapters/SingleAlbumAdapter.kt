package mustafaozhan.github.com.simplephotogallery.ui.adapters

import android.graphics.Bitmap
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.list_layout.view.*
import mustafaozhan.github.com.simplephotogallery.R

/**
 * Created by Mustafa Ozhan on 12/31/17 at 8:54 PM on Arch Linux.
 */
class SingleAlbumAdapter(val albumList: MutableList<String>, val context: Context, val options: RequestOptions, val glide: RequestBuilder<Bitmap>, val glideMain: RequestManager, val inOnItemClick: IOnItemClick) : RecyclerView.Adapter<SingleAlbumAdapter.ViewHolder>() {


    override fun onViewRecycled(holder: ViewHolder?) {
        if (holder != null) {
        }
        super.onViewRecycled(holder)
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        if (holder != null) {

        }
        super.onViewDetachedFromWindow(holder)
    }

    override fun getItemCount(): Int {
        return albumList.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindItems(albumList.get(position), glide, options, inOnItemClick)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_single_album_layout, parent, false)
        return ViewHolder(v)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(albumList: String, glide: RequestBuilder<Bitmap>, options: RequestOptions, inOnItemClick: AlbumFoldersAdapter.IOnItemClick) {

            glide.load(albumList).apply { options }.thumbnail(0.4f)
                    .into(itemView.thumbnail)

            itemView.setOnClickListener(object : View.OnClickListener {
                override fun onClick(p0: View?) {
                    inOnItemClick.onItemClick(albumList, false)
                }
            })
        }
    }
}