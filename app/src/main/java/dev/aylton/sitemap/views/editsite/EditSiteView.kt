package dev.aylton.sitemap.views.editsite


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.aylton.sitemap.R

/**
 * A simple [Fragment] subclass.
 */
class EditSiteView : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_site, container, false)
    }


}
