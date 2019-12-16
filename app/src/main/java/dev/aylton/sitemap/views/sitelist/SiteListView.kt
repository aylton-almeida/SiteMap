package dev.aylton.sitemap.views.sitelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dev.aylton.sitemap.R
import dev.aylton.sitemap.models.SiteModel
import dev.aylton.sitemap.views.BaseView
import kotlinx.android.synthetic.main.fragment_site_list.*
import org.jetbrains.anko.info

class SiteListView : BaseView(), SiteListener {

    private lateinit var presenter: SiteListPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_site_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = initPresenter(SiteListPresenter(this)) as SiteListPresenter

        init(toolbar, false)
    }

    override fun showSites(sites: List<SiteModel>) {
        recyclerView.adapter = SiteAdapter(sites, this)
    }

    override fun onSiteClick(site: SiteModel) {
        presenter.navigateSiteView(site)
    }

}
