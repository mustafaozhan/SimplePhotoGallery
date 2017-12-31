package mustafaozhan.github.com.simplephotogallery.ui.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.MenuItem
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

class MainActivity : AppCompatActivity(),AlbumFoldersAdapter.IOnItemClick {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val savedState = savedInstanceState

        if (savedState != null)
            folder_name = savedInstanceState!!.getString("folder_name")

        setSupportActionBar(my_toolbar)
        // Enable the Up button
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(resources.getDrawable(R.drawable.camera))

        setupNavigationView()

        var extra = intent.extras;
        if (extra != null) {
            var extraData = extra.get("image_url_data") as ArrayList<Albums>
            select_fragment(extraData)
        }

        drawer_layout_listener()
        supportActionBar!!.setTitle("Folders")
    }

    override fun onItemClick(position: String, isVideo: Boolean) {

        var bundle = Bundle()
        bundle.putString("folder_name", position)
        var intent = Intent(this, AlbumActivity::class.java)
        intent.putExtra("folder_name", position)
        startActivity(intent)
    }

    private var folder_name: String = ""

    public fun select_fragment(imagesList: ArrayList<Albums>) {

        val options = RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE).override(160, 160).skipMemoryCache(true).error(R.drawable.camera)
        val glide = Glide.with(this)

        val builder = glide.asBitmap()
        rvAlbums?.layoutManager = GridLayoutManager(this, 2)

        rvAlbums?.setHasFixedSize(true)

        // AlbumFoldersAdapter.kt is RecyclerView Adapter class. we will implement shortly.
        rvAlbums?.adapter = AlbumFoldersAdapter(imagesList, this, options, builder, glide, this)


        rvAlbums?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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

        fab_camera?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
             //   launchCamera()
            }
        }
        )
    }

    // drawer layout click listener in Kotlin source code.
    private fun drawer_layout_listener() {

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

        navigation.setNavigationItemSelectedListener(object : NavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                drawer_layout.closeDrawer(Gravity.START)
                when (item.itemId) {
//                    R.id.nav_all_folders -> {
//                    }
//                    R.id.nav_hidden_folders -> {
//                    }
                }
                return false
            }
        })
    }


}
