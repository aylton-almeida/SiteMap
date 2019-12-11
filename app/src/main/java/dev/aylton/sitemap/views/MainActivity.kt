package dev.aylton.sitemap.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.aylton.sitemap.R
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class MainActivity : AppCompatActivity(), AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        info("Started App")
    }

}
