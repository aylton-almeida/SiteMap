package dev.aylton.sitemap.views.sitelist

import android.os.Bundle
import android.view.*
import dev.aylton.sitemap.R
import dev.aylton.sitemap.models.SiteModel
import dev.aylton.sitemap.views.BaseView
import kotlinx.android.synthetic.main.fragment_site_list.*

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

        init(toolbar, upEnabled = false, optionsMenu = true)
    }

    override fun showSites(sites: List<SiteModel>) {
        recyclerView.adapter = SiteAdapter(sites, this)
    }

    override fun onSiteClick(site: SiteModel) {
        presenter.navigateSiteView(site)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_list, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_account -> presenter.navigateAccountView()
        }
        return super.onOptionsItemSelected(item)
    }

}
