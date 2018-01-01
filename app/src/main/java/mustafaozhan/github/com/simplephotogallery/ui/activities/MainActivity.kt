package mustafaozhan.github.com.simplephotogallery.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.widget.AbsListView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_content.*
import mustafaozhan.github.com.simplephotogallery.R
import mustafaozhan.github.com.simplephotogallery.model.Albums
import mustafaozhan.github.com.simplephotogallery.ui.adapters.AlbumFoldersAdapter

class MainActivity : AppCompatActivity(), AlbumFoldersAdapter.IOnItemClick {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null)
            folderName = savedInstanceState.getString("folderName")

        setSupportActionBar(my_toolbar)
        // Enable the Up button
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(resources.getDrawable(R.drawable.menu))

        setupNavigationView()

        val extra = intent.extras
        if (extra != null) {
            val extraData = extra.get("image_url_data") as ArrayList<Albums>
            selectFragment(extraData)
        }

        drawerLayoutListener()
        supportActionBar!!.title = "Folders"
    }

    override fun onItemClick(position: String, isVideo: Boolean) {

        val bundle = Bundle()
        bundle.putString("folderName", position)
        val intent = Intent(this, AlbumActivity::class.java)
        intent.putExtra("folderName", position)
        startActivity(intent)
    }

    private var folderName: String = ""

    @SuppressLint("CheckResult")
    private fun selectFragment(imagesList: ArrayList<Albums>) {

        RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE).override(160, 160).skipMemoryCache(true).error(R.drawable.camera)
        val glide = Glide.with(this)

        val builder = glide.asBitmap()
        rvAlbums?.layoutManager = GridLayoutManager(this, 2)

        rvAlbums?.setHasFixedSize(true)

        // AlbumFoldersAdapter.kt is RecyclerView Adapter class. we will implement shortly.
        rvAlbums?.adapter = AlbumFoldersAdapter(imagesList, builder, this)


        rvAlbums?.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                when (newState) {
                    RecyclerView.SCROLL_STATE_IDLE -> glide.resumeRequests()
                    AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL, AbsListView.OnScrollListener.SCROLL_STATE_FLING -> glide.pauseRequests()
                }
            }
        }
        )

        fab_camera?.setOnClickListener {
            //   launchCamera()
        }
    }

    // drawer layout click listener in Kotlin source code.
    private fun drawerLayoutListener() {

        drawer_layout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {

            }

            override fun onDrawerClosed(drawerView: View) {
            }

            override fun onDrawerOpened(drawerView: View) {
            }

            override fun onDrawerStateChanged(newState: Int) {
            }


        }
        )
    }

    // Navigation item click listener Kotlin source code.
    private fun setupNavigationView() {

        navigation.setNavigationItemSelectedListener { item ->
            drawer_layout.closeDrawer(Gravity.START)
            when (item.itemId) {
//                    R.id.nav_all_folders -> {
//                    }
//                    R.id.nav_hidden_folders -> {
//                    }
            }
            false
        }
    }


}
