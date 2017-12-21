package mustafaozhan.github.com.simplephotogallery.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import mustafaozhan.github.com.simplephotogallery.R
import mustafaozhan.github.com.simplephotogallery.model.Albums

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
        supportActionBar!!.setHomeAsUpIndicator(resources.getDrawable(R.drawable.ic_launcher_foreground))

        setupNavigationView()

        val extra = intent.extras
        if (extra != null) {
            val extraData = extra.get("img_url_data") as ArrayList<*>
            selectFragment(extraData)
        }

        drawerLayoutListenner()

        supportActionBar!!.setTitle("Folders")


    }


}
