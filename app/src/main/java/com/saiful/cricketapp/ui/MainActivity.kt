package com.saiful.cricketapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.saiful.cricketapp.R
import com.saiful.cricketapp.databinding.ActivityMainBinding
import com.saiful.cricketapp.sharedPreferences.UtilsSharedPreferences
import com.saiful.cricketapp.util.Constant.Companion.NOTIFY_USER

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Navigation Controller
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.homeFragment, R.id.matchesFragment, R.id.seriesFragment, R.id.moreFragment)
        )
        navController = navHostFragment.findNavController()
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.bottomNavigation.setupWithNavController(navController)

        val utilsSharedPreferences = UtilsSharedPreferences(this)
        utilsSharedPreferences.saveData(NOTIFY_USER, "true")
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}