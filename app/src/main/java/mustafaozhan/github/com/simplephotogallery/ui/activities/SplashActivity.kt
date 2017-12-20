package mustafaozhan.github.com.simplephotogallery.ui.activities

import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.ActivityChooserView
import android.widget.Toast
import mustafaozhan.github.com.simplephotogallery.R
import java.util.jar.Manifest

class SplashActivity : AppCompatActivity() {
    companion object {
        internal var SPLASH_TIME_OUT = 800
        var WRITE_EXTERNAL_STORAGE_CODE = 6036
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
}
