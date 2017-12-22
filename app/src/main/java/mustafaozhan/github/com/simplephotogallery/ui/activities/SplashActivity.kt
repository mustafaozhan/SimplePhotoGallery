package mustafaozhan.github.com.simplephotogallery.ui.activities

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.Toast
import mustafaozhan.github.com.simplephotogallery.R
import mustafaozhan.github.com.simplephotogallery.model.Albums

class SplashActivity : AppCompatActivity() {
    companion object {
        internal var SPLASH_TIME_OUT = 800
        var WRITE_EXTERNAL_STORAGE_CODE = 6036
        val BUCKET_GROUP_BY = "1) GROUP BY 1,(2"
        val BUCKET_ORDER_BY = "MAX(datetaken) DESC"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            if (!checkSelfPermission())
                requestPermission()
            else
                loadAllImages()
        }, SPLASH_TIME_OUT.toLong())
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), WRITE_EXTERNAL_STORAGE_CODE)
    }

    private fun checkSelfPermission() = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            WRITE_EXTERNAL_STORAGE_CODE -> {
                if (grantResults.isNotEmpty()) {
                    val permissionGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED
                    if (permissionGranted)
                        loadAllImages()
                    else
                        Toast.makeText(this, "Permission Denied! Cannot load images.", Toast.LENGTH_SHORT).show()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun loadAllImages() {
        val imageList = getAllShownImagesPath(this)
        Intent(this, MainActivity::class.java).apply {
            putParcelableArrayListExtra("image_url_data", imageList)
            startActivity(this)
        }
        finish()

    }

    private fun getAllShownImagesPath(activity: Activity): ArrayList<Albums> {
        val uri: Uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val cursor: Cursor
        var cursorBucket: Cursor? = null
        val columnIndexData: Int
        val columnIndexFolderName: Int
        val listOfAllImages = ArrayList<String>()
        var absolutePathOfImage: String?
        val albumsList = ArrayList<Albums>()


        val projection = arrayOf(MediaStore.Images.ImageColumns.BUCKET_ID,
                MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
                MediaStore.Images.ImageColumns.DATE_TAKEN,
                MediaStore.Images.ImageColumns.DATA)

        cursor = activity.contentResolver.query(uri, projection, BUCKET_GROUP_BY, null, BUCKET_ORDER_BY)

        if (cursor != null) {
            columnIndexData = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
            columnIndexFolderName = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
            while (cursor.moveToNext()) {
                absolutePathOfImage = cursor.getString(columnIndexData)
                Log.d("title_apps", "bucket name:" + cursor.getString(columnIndexData))

                val selectionArgs = arrayOf("%" + cursor.getString(columnIndexFolderName) + "%")
                val selection = MediaStore.Images.Media.DATA + " like ? "
                val projectionOnlyBucket = arrayOf(MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME)

                cursorBucket = activity.contentResolver.query(uri, projectionOnlyBucket, selection, selectionArgs, null)
                Log.d("title_apps", "bucket size:" + cursorBucket.count)

                if (absolutePathOfImage != "" && absolutePathOfImage != null) {
                    listOfAllImages.add(absolutePathOfImage)
                    albumsList.add(Albums(cursor.getString(columnIndexFolderName), absolutePathOfImage, cursorBucket.count, false))
                }
            }
        }
        cursor.close()
        cursorBucket?.close()
        return getListOfVideoFolders(albumsList)
    }

    private fun getListOfVideoFolders(albumsList: ArrayList<Albums>): ArrayList<Albums> {

        val cursor: Cursor
        var cursorBucket: Cursor? = null
        val uri: Uri = android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        val columnIndexAlbumName: Int
        val columnIndexAlbumVideo: Int

        val projection1 = arrayOf(MediaStore.Video.VideoColumns.BUCKET_ID,
                MediaStore.Video.VideoColumns.BUCKET_DISPLAY_NAME,
                MediaStore.Video.VideoColumns.DATE_TAKEN,
                MediaStore.Video.VideoColumns.DATA)

        cursor = this.contentResolver.query(uri, projection1, BUCKET_GROUP_BY, null, BUCKET_ORDER_BY)

        if (cursor != null) {
            columnIndexAlbumName = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME)
            columnIndexAlbumVideo = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
            while (cursor.moveToNext()) {
                Log.d("title_apps", "bucket video:" + cursor.getString(columnIndexAlbumName))
                Log.d("title_apps", "bucket video:" + cursor.getString(columnIndexAlbumVideo))
                val selectionArgs = arrayOf("%" + cursor.getString(columnIndexAlbumName) + "%")

                val selection = MediaStore.Video.Media.DATA + " like ? "
                val projectionOnlyBucket = arrayOf(MediaStore.MediaColumns.DATA, MediaStore.Video.Media.BUCKET_DISPLAY_NAME)

                cursorBucket = this.contentResolver.query(uri, projectionOnlyBucket, selection, selectionArgs, null)
                Log.d("title_apps", "bucket size:" + cursorBucket.count)

                albumsList.add(Albums(cursor.getString(columnIndexAlbumName), cursor.getString(columnIndexAlbumVideo), cursorBucket.count, true))
            }
        }
        cursor.close()
        cursorBucket?.close()
        return albumsList
    }
}
