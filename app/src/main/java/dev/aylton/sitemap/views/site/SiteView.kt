package dev.aylton.sitemap.views.site


import android.os.Bundle
import android.view.*
import com.bumptech.glide.Glide
import com.google.android.gms.maps.GoogleMap
import com.synnapps.carouselview.ImageListener
import dev.aylton.sitemap.R
import dev.aylton.sitemap.models.SiteModel
import dev.aylton.sitemap.models.UserModel
import dev.aylton.sitemap.views.BaseView
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter
import kotlinx.android.synthetic.main.fragment_site.*
import kotlinx.android.synthetic.main.nav_header_main.*
import org.jetbrains.anko.info
import java.text.SimpleDateFormat
import java.util.*

class SiteView : BaseView() {

    private lateinit var presenter: SitePresenter
    private lateinit var map: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_site, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = initPresenter(SitePresenter(this)) as SitePresenter

        init(upEnabled = true, optionsMenu = true)

        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync {
            map = it
            presenter.populateMap(map)
        }

        speedDial.setMenuListener(object : SimpleMenuListenerAdapter() {
            override fun onMenuItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.item_edit_site -> presenter.navigateEditSite()
                    R.id.item_delete_site -> presenter.deleteSite()
                }
                return false
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_site, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_notes -> presenter.navigateToNotes()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showSiteWithUser(site: SiteModel, user: UserModel) {
        // Update Images
        val imageListener =
            ImageListener { position, imageView ->
                Glide
                    .with(this)
                    .load(site.images[position])
                    .centerCrop()
                    .into(imageView)
            }

        // Update general data
        textName.text = site.name
        textDescription.text = site.description
        val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.GERMAN)
        val visitedDate = if (site.visited) dateFormat.format(site.visitedDate) else "No"
        textVisited.text = String.format(resources.getString(R.string.visited_in), visitedDate)
        carouselView.setImageListener(imageListener)
        carouselView.pageCount = site.images.size

        // Rating system
        if (site.rating.any { it.positive && it.userId == user.id }) {
            imageViewThumbsUp.setImageDrawable(context?.getDrawable(R.drawable.ic_thumb_up))
            imageViewThumbsUp.setOnClickListener {
                presenter.removeRating(true)
            }
        } else{
            imageViewThumbsUp.setImageDrawable(context?.getDrawable(R.drawable.ic_thumb_up_outlined))
            imageViewThumbsUp.setOnClickListener {
                presenter.addRating(true)
            }
        }
        if (site.rating.any { !it.positive && it.userId == user.id }) {
            imageViewThumbsDown.setImageDrawable(context?.getDrawable(R.drawable.ic_thumb_down))
            imageViewThumbsDown.setOnClickListener {
                presenter.removeRating(false)
            }
        } else {
            imageViewThumbsDown.setImageDrawable(context?.getDrawable(R.drawable.ic_thumb_down_outlined))
            imageViewThumbsDown.setOnClickListener {
                presenter.addRating(false)
            }
        }
        valueLikes.text = site.getLikes().toString()
        valueDislikes.text = site.getDislikes().toString()

        // Update location
        textLat.text = String.format(resources.getString(R.string.latitude), site.location.lat)
        textLng.text = String.format(resources.getString(R.string.longitude), site.location.lng)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }
}
