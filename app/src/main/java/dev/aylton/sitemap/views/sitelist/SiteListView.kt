package dev.aylton.sitemap.views.sitelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import dev.aylton.sitemap.R
import dev.aylton.sitemap.views.BaseView
import kotlinx.android.synthetic.main.fragment_site_list.*

class SiteListView : BaseView() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_site_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init(toolbar, true)

        button.setOnClickListener{
            findNavController().navigate(R.id.second_dest)
        }
    }
}
