package dev.aylton.sitemap.views.editsite


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.aylton.sitemap.R
import dev.aylton.sitemap.views.BaseView
import kotlinx.android.synthetic.main.fragment_edit_site.*

class EditSiteView : BaseView() {

    private lateinit var presenter: EditSitePresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_site, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = initPresenter(EditSitePresenter(this)) as EditSitePresenter

        init(toolbar, upEnabled = true, optionsMenu = false)
    }

}
