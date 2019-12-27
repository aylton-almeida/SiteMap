package dev.aylton.sitemap.views.sitelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import dev.aylton.sitemap.R
import dev.aylton.sitemap.views.BaseView
import kotlinx.android.synthetic.main.fragment_tabs.*

class SiteListView : BaseView() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tabs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        init(toolbar, upEnabled = false, optionsMenu = true)

        viewPager.adapter = TabsAdapter(this)

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = if (position == 0) "Public List"
            else "Private List"
        }.attach()
    }
}