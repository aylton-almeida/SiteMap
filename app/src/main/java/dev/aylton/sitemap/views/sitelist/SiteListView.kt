package dev.aylton.sitemap.views.sitelist

import android.os.Bundle
import android.view.*
import com.google.android.material.tabs.TabLayoutMediator
import dev.aylton.sitemap.R
import dev.aylton.sitemap.models.SiteModel
import dev.aylton.sitemap.views.BaseView
import kotlinx.android.synthetic.main.fragment_tabs.*

class SiteListView : BaseView() {

    lateinit var presenter: SiteListPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tabs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        presenter = initPresenter(SiteListPresenter(this)) as SiteListPresenter

        init(toolbar, upEnabled = false, optionsMenu = true)

        viewPager.adapter = TabsAdapter(this)

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = if (position == 0) "Public List"
            else "Private List"
        }.attach()
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