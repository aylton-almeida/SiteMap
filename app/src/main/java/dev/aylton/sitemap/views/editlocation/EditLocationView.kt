package dev.aylton.sitemap.views.editlocation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import dev.aylton.sitemap.R
import dev.aylton.sitemap.models.Location
import kotlinx.android.synthetic.main.activity_edit_location.*
import org.jetbrains.anko.AnkoLogger

class EditLocationView : AppCompatActivity(), GoogleMap.OnMarkerDragListener,
    GoogleMap.OnMarkerClickListener, AnkoLogger {

    lateinit var presenter: EditLocationPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_location)

        toolbar.title = "Choose Location"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        presenter = EditLocationPresenter(this)

        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync {
            it.setOnMarkerDragListener(this)
            it.setOnMarkerClickListener(this)
            presenter.doConfigureMap(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_edit_location, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                this.onBackPressed()
            }
            R.id.item_save -> {
                presenter.doSave()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onMarkerDragEnd(marker: Marker) {
        presenter.doUpdateLocation(marker.position.latitude, marker.position.longitude)
    }

    override fun onMarkerDragStart(p0: Marker?) {
    }

    override fun onMarkerDrag(marker: Marker) {
        textLat.text = String.format(resources.getString(R.string.latitude), marker.position.latitude)
        textLng.text = String.format(resources.getString(R.string.longitude), marker.position.longitude)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        presenter.doUpdateMarker(marker)
        return false
    }

    override fun onDestroy() {
        super.onDestroy()
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    fun showLocation(location: Location){
        textLat.text = String.format(resources.getString(R.string.latitude), location.lat)
        textLng.text = String.format(resources.getString(R.string.longitude), location.lng)
    }
}
