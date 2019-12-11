package dev.aylton.sitemap.models.firebase

import android.content.Context
import dev.aylton.sitemap.models.SiteModel
import dev.aylton.sitemap.models.SiteStore
import org.jetbrains.anko.AnkoLogger

class SiteFireStore(val context: Context) : SiteStore, AnkoLogger {
    override fun findAll(): List<SiteModel> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun create(site: SiteModel) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun update(site: SiteModel) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(site: SiteModel) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findById(id: Long): SiteModel? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clear() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}