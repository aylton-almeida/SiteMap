package dev.aylton.sitemap.views

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import dev.aylton.sitemap.R
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class MainActivity : AppCompatActivity(), AnkoLogger {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_main)
        info("Started App")

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
                }
                R.id.auth_dest -> appBarLayout.visibility = View.GONE
                else -> {
                    appBarLayout.visibility = View.VISIBLE
                    tabs.visibility = View.GONE
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.navHostFragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
