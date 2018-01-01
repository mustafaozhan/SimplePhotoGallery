package mustafaozhan.github.com.simplephotogallery.ui.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.database.Cursor
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.AbsListView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_album.*
import mustafaozhan.github.com.simplephotogallery.R
import mustafaozhan.github.com.simplephotogallery.ui.adapters.AlbumFoldersAdapter
import mustafaozhan.github.com.simplephotogallery.ui.adapters.SingleAlbumAdapter

class AlbumActivity : AppCompatActivity(), AlbumFoldersAdapter.IOnItemClick {
    override fun onItemClick(position: String, isVideo: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private var adapter: SingleAlbumAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album)

        setSupportActionBar(my_album_toolbar)
        // Enable the Up button
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val folderName = intent.getStringExtra("folder_name")
        supportActionBar!!.title = "" + folderName
        intent.getBooleanExtra("isVideo", false)
        initUiViews(folderName)

    }


    @SuppressLint("CheckResult")
    private fun initUiViews(folderName: String?) {

        RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE).override(160, 160).skipMemoryCache(true).error(R.drawable.icon)
        val glide = Glide.with(this)
        val builder = glide.asBitmap()

        rvAlbumSelected.layoutManager = GridLayoutManager(this, 2)
        rvAlbumSelected?.setHasFixedSize(true)
        adapter = SingleAlbumAdapter(getAllShownImagesPath(this, folderName), builder, this)
        rvAlbumSelected?.adapter = adapter

        rvAlbumSelected?.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                when (newState) {
                    RecyclerView.SCROLL_STATE_IDLE -> glide.resumeRequests()
                    AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL, AbsListView.OnScrollListener.SCROLL_STATE_FLING -> glide.pauseRequests()
                }
            }
        }
        )
    }

// Read all images path from specified directory.

    private fun getAllShownImagesPath(activity: Activity, folderName: String?): MutableList<String> {

        val uri: Uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val cursorBucket: Cursor
        val columnIndexData: Int
        val listOfAllImages = ArrayList<String>()
        var absolutePathOfImage: String?

        val selectionArgs = arrayOf("%$folderName%")

        val selection = MediaStore.Images.Media.DATA + " like ? "

        val projectionOnlyBucket = arrayOf(MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME)

        cursorBucket = activity.contentResolver.query(uri, projectionOnlyBucket, selection, selectionArgs, null)

        columnIndexData = cursorBucket.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)

        while (cursorBucket.moveToNext()) {
            absolutePathOfImage = cursorBucket.getString(columnIndexData)
            if (absolutePathOfImage != "" && absolutePathOfImage != null)
                listOfAllImages.add(absolutePathOfImage)
        }
        cursorBucket.close()
        return listOfAllImages.asReversed()
    }

}
