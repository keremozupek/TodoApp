
package com.example.todoapp

import android.app.AlarmManager
import android.app.PendingIntent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.todoapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

private lateinit var binding: ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment, R.id.settingsFragment))
        val navController = findNavController(R.id.fragment)
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.bottomNavigationView.setupWithNavController(navController)
    }
}
