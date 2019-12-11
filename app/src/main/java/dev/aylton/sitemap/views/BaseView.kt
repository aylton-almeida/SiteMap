package dev.aylton.sitemap.views

import android.graphics.Color
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
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

    private fun Snackbar.withColor(color: Int): Snackbar{
        this.view.setBackgroundColor(color)
        return this
    }

    fun showSnackbar(text: String, color: Int){
        Snackbar.make(view!!, text, Snackbar.LENGTH_SHORT)
            .withColor(Color.RED).show()
    }

    fun showProgress(progressBar: ProgressBar?) {
        progressBar?.visibility = View.VISIBLE
    }
    fun hideProgress(progressBar: ProgressBar?) {
        progressBar?.visibility = View.INVISIBLE
    }

    open fun showSite(site: SiteModel) {}
    open fun showPlacemarks(site: SiteModel) {}
}