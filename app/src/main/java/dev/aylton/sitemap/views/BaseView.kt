package dev.aylton.sitemap.views

import android.content.Intent
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dev.aylton.sitemap.models.Note
import dev.aylton.sitemap.models.SiteModel
import dev.aylton.sitemap.models.UserModel
import dev.aylton.sitemap.views.sitelist.TabsAdapter
import org.jetbrains.anko.AnkoLogger


const val IMAGE_REQUEST = 1
const val LOCATION_REQUEST = 2

abstract class BaseView : Fragment(), AnkoLogger {
    private var basePresenter: BasePresenter? = null

    fun init(toolbar: Toolbar, upEnabled: Boolean, optionsMenu: Boolean, title: String = "") {
        val act = activity as AppCompatActivity
        if (title == "")
            toolbar.title = act.title
        else
            toolbar.title = title
        act.setSupportActionBar(toolbar)
        setHasOptionsMenu(optionsMenu)
        act.supportActionBar?.setDisplayHomeAsUpEnabled(upEnabled)
        act.supportActionBar?.setDisplayShowHomeEnabled(upEnabled)
    }

    fun initPresenter(presenter: BasePresenter): BasePresenter {
        basePresenter = presenter
        return presenter
    }

    fun initTabs(viewPager: ViewPager2, tabs: TabLayout){
        viewPager.adapter = TabsAdapter(this)

        TabLayoutMediator(tabs,viewPager) { tab, position ->
            tab.text = if (position == 0) "Public List"
            else "Private List"
        }.attach()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            basePresenter?.doActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onDestroy() {
        basePresenter?.onDestroy()
        super.onDestroy()
    }

    private fun Snackbar.withColor(color: Int): Snackbar {
        this.view.setBackgroundColor(color)
        return this
    }

    fun showSnackbar(text: String, color: Int) {
        Snackbar.make(view!!, text, Snackbar.LENGTH_SHORT)
            .withColor(color).show()
    }

    fun showProgress(progressBar: ProgressBar?) {
        progressBar?.visibility = View.VISIBLE
    }

    fun hideProgress(progressBar: ProgressBar?) {
        progressBar?.visibility = View.INVISIBLE
    }

    open fun showSite(site: SiteModel) {}
    open fun showSites(sites: List<SiteModel>) {}
    open fun toggleEnable(isEnabled: Boolean) {}
    open fun showUserData(user: UserModel, numPublicSites: Int, numPrivateSites: Int) {}
    open fun showNotes(notes: ArrayList<Note>){}
}