package dev.aylton.sitemap.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.aylton.sitemap.R
import dev.aylton.sitemap.models.SiteModel
import kotlinx.android.synthetic.main.card_site.view.*
import org.jetbrains.anko.AnkoLogger

interface SiteListener {
    fun onSiteClick(site: SiteModel)

    fun onSiteChecked(site: SiteModel, isChecked: Boolean)
}

class SiteListAdapter(private var sites: List<SiteModel>,
                      private val listener: SiteListener
) : RecyclerView.Adapter<SiteListAdapter.ViewHolder>(), AnkoLogger {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.card_site,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val site = sites[position]
        holder.bind(site, listener)
    }

    override fun getItemCount(): Int = sites.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), AnkoLogger {

        fun bind(site: SiteModel, listener: SiteListener) {
            itemView.siteName.text = site.name
            itemView.siteLocation.text = String.format(itemView.resources.getString(R.string.latlng), site.location.lat, site.location.lng)
            itemView.checkbox.isChecked = site.visited
            Glide.with(itemView.context).load(site.images.first()).into(itemView.imageIcon)
            itemView.setOnClickListener { listener.onSiteClick(site) }
            itemView.checkbox.setOnClickListener { listener.onSiteChecked(site, itemView.checkbox.isChecked) }
        }
    }
}