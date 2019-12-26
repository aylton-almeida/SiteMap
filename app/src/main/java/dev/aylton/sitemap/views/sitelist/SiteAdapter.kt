package dev.aylton.sitemap.views.sitelist

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

class SiteAdapter(private var sites: List<SiteModel>,
                                   private val listener: SiteListener
) : RecyclerView.Adapter<SiteAdapter.ViewHolder>(), AnkoLogger {

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
            itemView.siteDescription.text = site.description
            itemView.checkbox.isChecked = site.isVisited
            Glide.with(itemView.context).load(site.image).into(itemView.imageIcon)
            itemView.setOnClickListener { listener.onSiteClick(site) }
            itemView.checkbox.setOnClickListener { listener.onSiteChecked(site, itemView.checkbox.isChecked) }
        }
    }
}