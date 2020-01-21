package dev.aylton.sitemap.views.main

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import dev.aylton.sitemap.R
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class MainView : AppCompatActivity(), AnkoLogger {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_main)
        info("Started App")

        presenter = MainPresenter(this)

        setSupportActionBar(toolbar)

        val navController = findNavController(R.id.navHostFragment)

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.siteList_dest, R.id.favorites_dest, R.id.sitesMap_dest), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.siteList_dest -> {
                    appBarLayout.visibility = View.VISIBLE
                    tabs.visibility = View.VISIBLE
                    navView.getHeaderView(0).findViewById<TextView>(R.id.drawerHeaderEmail).text = presenter.fireStore.getCurrentUserEmail()
                }
                R.id.auth_dest -> appBarLayout.visibility = View.GONE
                else -> {
                    appBarLayout.visibility = View.VISIBLE
                    tabs.visibility = View.GONE
                }
            }
        }

        iconExit.setOnClickListener{
            presenter.doSignOut()
        }

        navView.getHeaderView(0).setOnClickListener{
            presenter.navigateToAccount()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.navHostFragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
