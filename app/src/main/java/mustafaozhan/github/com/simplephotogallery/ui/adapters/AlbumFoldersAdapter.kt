package mustafaozhan.github.com.simplephotogallery.ui.adapters

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.RequestBuilder
import kotlinx.android.synthetic.main.list_layout.view.*
import mustafaozhan.github.com.simplephotogallery.R
import mustafaozhan.github.com.simplephotogallery.model.Albums

/**
 * Created by Mustafa Ozhan on 12/22/17 at 12:15 PM on Arch Linux.
 */
class AlbumFoldersAdapter(private val albumList: ArrayList<Albums>,
                          private val glide: RequestBuilder<Bitmap>,
                          private val inOnItemClick: IOnItemClick) : RecyclerView.Adapter<AlbumFoldersAdapter.ViewHolder>() {


    override fun onViewRecycled(holder: ViewHolder?) {

        if (holder != null) {
            //glideMain.clear(holder.itemView.thumbnail)
            // glide.clear(holder.itemView.thumbnail)
            //Glide.get(context).clearMemory()
            // holder?.itemView?.thumbnail?.setImageBitmap(null)
        }// Glide.clear(holder?.itemView?.thumbnail)
        super.onViewRecycled(holder)
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder?) {
        if (holder != null) {
            // glideMain.clear(holder.itemView.thumbnail)
            //Glide.get(context).clearMemory()
            // holder?.itemView?.thumbnail?.setImageBitmap(null)
        }

        super.onViewDetachedFromWindow(holder)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.list_layout, parent, false)
        return ViewHolder(v)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindItems(albumList[position], glide, inOnItemClick, albumList[position].isVideo)
        holder?.itemView?.title?.text = albumList[position].folderNames
        if (albumList[position].isVideo)
            holder?.itemView?.photoCount?.text = "" + albumList[position].imageCount + " videos"
        else
            holder?.itemView?.photoCount?.text = "" + albumList[position].imageCount + " photos"

    }

    override fun getItemCount() = albumList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(albumList: Albums, glide: RequestBuilder<Bitmap>, inOnItemClick: IOnItemClick, isVideo: Boolean) {
            glide.load(albumList.imagePath).thumbnail(0.4f)
                    .into(itemView.thumbnail)

            itemView.setOnClickListener { inOnItemClick.onItemClick(albumList.folderNames, isVideo) }

        }

    }

    interface IOnItemClick {
        fun onItemClick(position: String, isVideo: Boolean)
    }
}