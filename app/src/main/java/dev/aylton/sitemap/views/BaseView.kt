package dev.aylton.sitemap.views

import android.content.Intent
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
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


const val LOCAL_IMAGE_REQUEST = 1
const val CAMERA_IMAGE_REQUEST = 2
const val LOCATION_REQUEST = 3

abstract class BaseView : Fragment(), AnkoLogger {
    private var basePresenter: BasePresenter? = null

    fun init(upEnabled: Boolean, optionsMenu: Boolean) {
        val act = activity as AppCompatActivity
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
        if (data != null) basePresenter?.doActivityResult(requestCode, resultCode, data)
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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        basePresenter?.doRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    open fun showSite(site: SiteModel) {}
    open fun showSites(sites: List<SiteModel>) {}
    open fun showSiteWithUser(site: SiteModel, user: UserModel) {}
    open fun toggleEnable(isEnabled: Boolean) {}
    open fun showUserData(user: UserModel, numPublicSites: Int, numPrivateSites: Int) {}
    open fun showNotes(notes: ArrayList<Note>){}
}