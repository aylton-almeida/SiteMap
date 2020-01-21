package dev.aylton.sitemap.views.main

import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import dev.aylton.sitemap.MainApp
import dev.aylton.sitemap.R
import dev.aylton.sitemap.services.firebase.SiteFireStore
import kotlinx.android.synthetic.main.activity_main.*

class MainPresenter(var view: MainView){

    var app: MainApp = view.application as MainApp
    var fireStore: SiteFireStore

    init {
        fireStore = app.site as SiteFireStore
    }

    fun doSignOut(){
        fireStore.signOut()
        view.findNavController(R.id.navHostFragment).navigate(R.id.auth_dest)
        view.drawerLayout.closeDrawer(GravityCompat.START, true)
    }

    fun navigateToAccount(){
        view.findNavController(R.id.navHostFragment).navigate(R.id.account_dest)
        view.drawerLayout.closeDrawer(GravityCompat.START, true)
    }
}