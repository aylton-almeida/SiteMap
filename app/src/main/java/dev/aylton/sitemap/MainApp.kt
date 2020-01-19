package dev.aylton.sitemap

import android.app.Application
import dev.aylton.sitemap.services.SiteStore
import dev.aylton.sitemap.services.firebase.SiteFireStore
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class MainApp: Application(), AnkoLogger {

    lateinit var site: SiteStore

    override fun onCreate() {
        super.onCreate()
        site = SiteFireStore(applicationContext)
        info("Site App started")
    }
}