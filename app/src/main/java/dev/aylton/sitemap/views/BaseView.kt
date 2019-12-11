package dev.aylton.sitemap.views

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import dev.aylton.sitemap.models.SiteModel
import org.jetbrains.anko.AnkoLogger


abstract class BaseView: Fragment(), AnkoLogger{
    private var basePresenter: BasePresenter? = null

    fun init(toolbar: Toolbar, upEnabled: Boolean){
        val act = activity as AppCompatActivity
        toolbar.title = act.title
        act.setSupportActionBar(toolbar)
        act.supportActionBar?.setDisplayHomeAsUpEnabled(upEnabled)
        act.supportActionBar?.setDisplayShowHomeEnabled(upEnabled)
    }

    fun initPresenter(presenter: BasePresenter): BasePresenter{
        basePresenter = presenter
        return presenter
    }

    override fun onDestroy() {
        basePresenter?.onDestroy()
        super.onDestroy()
    }

    open fun showSite(site: SiteModel) {}
    open fun showPlacemarks(site: SiteModel) {}
    open fun showProgress() {}
    open fun hideProgress() {}
}