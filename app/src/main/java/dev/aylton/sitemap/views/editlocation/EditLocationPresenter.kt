package dev.aylton.sitemap.views.editlocation

import android.content.Intent
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import dev.aylton.sitemap.models.Location

class EditLocationPresenter(val view: EditLocationView) {

    var location: Location = view.intent.extras?.getParcelable("location")!!

    init {
        view.showLocation(location)
    }

    fun doConfigureMap(map: GoogleMap) {
        val loc = LatLng(location.lat, location.lng)
        val options = MarkerOptions()
            .title("Site")
            .snippet("GPS: $loc")
            .draggable(true)
            .position(loc)
        map.addMarker(options)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, location.zoom))
    }

    fun doUpdateLocation(lat: Double, lng: Double) {
        location.lat = lat
        location.lng = lng
    }

    fun doUpdateMarker(marker: Marker) {
        val loc = LatLng(location.lat, location.lng)
        marker.snippet = "GPS : $loc"
    }

    fun doSave() {
        val resultIntent = Intent()
        resultIntent.putExtra("location", location)
        view.setResult(0, resultIntent)
        view.finish()
    }
}