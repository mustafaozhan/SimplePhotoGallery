package mustafaozhan.github.com.simplephotogallery.ui.activities

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

class AlbumActivity : AppCompatActivity() {
    private var adapter: SingleAlbumAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album)

        setSupportActionBar(my_album_toolbar)
        // Enable the Up button
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val folderName = intent.getStringExtra("folder_name")
        supportActionBar!!.setTitle("" + folderName)
        val isVideo = intent.getBooleanExtra("isVideo", false)
        initUiViews(folderName, isVideo)

    }


    private fun initUiViews(folderName: String?, isVideo: Boolean?) {

        val options = RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE).override(160, 160).skipMemoryCache(true).error(R.drawable.ic_image_unavailable)
        val glide = Glide.with(this)
        val builder = glide.asBitmap()

        rvAlbumSelected.layoutManager = GridLayoutManager(this, 2)
        rvAlbumSelected?.setHasFixedSize(true)
        adapter = SingleAlbumAdapter(getAllShownImagesPath(this, folderName, isVideo), this, options, builder, glide, this)
        rvAlbumSelected?.adapter = adapter

        rvAlbumSelected?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }

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

    private fun getAllShownImagesPath(activity: Activity, folderName: String?, isVideo: Boolean?): MutableList<String> {

        val uri: Uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val cursorBucket: Cursor
        val columnIndexData: Int
        val listOfAllImages = ArrayList<String>()
        var absolutePathOfImage: String? = null

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
        return listOfAllImages.asReversed()
    }

}
