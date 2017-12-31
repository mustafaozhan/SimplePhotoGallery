package mustafaozhan.github.com.simplephotogallery.ui.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AccelerateInterpolator
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_single.*
import mustafaozhan.github.com.simplephotogallery.R

class SingleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single)

        setSupportActionBar(toolbar)
        // Enable the Up button
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        val folder_name = intent.getStringExtra("folder_name")
        Glide.with(this).load(folder_name).into(imageFullScreenView)

        Handler().postDelayed(Runnable
        {
            if (supportActionBar != null)
                appbar.animate().translationY(-appbar.bottom.toFloat()).setInterpolator(AccelerateInterpolator()).start()
            //isAppBarShown = false
        }, 1500)
    }
}
