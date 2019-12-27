package dev.aylton.sitemap.views.sitelist

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import dev.aylton.sitemap.views.sitelist.privatesitelist.PrivateSiteListView
import dev.aylton.sitemap.views.sitelist.publicsitelist.PublicSiteListView
import org.jetbrains.anko.AnkoLogger

class TabsAdapter(fragment: Fragment) : FragmentStateAdapter(fragment), AnkoLogger {

    override fun createFragment(position: Int): Fragment {
        var f: Fragment = PublicSiteListView()
        if (position != 0)
            f = PrivateSiteListView()

        return f
    }

    override fun getItemCount(): Int = 2
}
