package com.programmalar.adiblar

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import com.programmalar.adiblar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var activityMainBinding: ActivityMainBinding
    lateinit var sharedPreferences: SharedPreferences
    var MYPREFERENCES="nightModePrefe"
    var KEY_ISNIGHTMODE="nightModePrefe"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        sharedPreferences = getSharedPreferences(MYPREFERENCES, Context.MODE_PRIVATE)!!
        checkNightModeActivited()
    }

    override fun onNavigateUp(): Boolean {
        return findNavController(R.id.fragment).navigateUp()
    }
    private fun checkNightModeActivited(){
        if (sharedPreferences.getBoolean(KEY_ISNIGHTMODE,false)){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}