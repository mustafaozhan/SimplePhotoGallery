package mustafaozhan.github.com.simplephotogallery.ui.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.view.Gravity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import mustafaozhan.github.com.simplephotogallery.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val savedState = savedInstanceState
        val folderName: String?

        if (savedInstanceState != null)
            folderName = savedInstanceState.getString("folder_name")

        setSupportActionBar(mToolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(resources.getDrawable(R.drawable.menu))


        val extra = intent.extras
        if (extra != null) {
            val extraData = extra.get("img_url_data") as ArrayList<*>
//            selectFragment(extraData)
        }

        setListeners()


    }

    private fun setListeners() {

        mNavigation.setNavigationItemSelectedListener { item ->
            mDrawerLayout.closeDrawer(Gravity.START)
            when (item.itemId) {

            }
            false
        }


        mDrawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerStateChanged(newState: Int) {
            }

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
            }

            override fun onDrawerClosed(drawerView: View) {
                supportActionBar!!.setHomeAsUpIndicator(resources.getDrawable(R.drawable.menu))
                supportActionBar!!.title = resources.getString(R.string.app_name)
            }

            override fun onDrawerOpened(drawerView: View) {
                supportActionBar!!.setHomeAsUpIndicator(resources.getDrawable(R.drawable.abc_ic_ab_back_material))
                supportActionBar!!.title = "Folders"
            }

        })
    }


}
