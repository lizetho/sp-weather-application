package com.viseo.poc.myweatherapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.viseo.poc.myweatherapplication.ui.home.HomePageFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, HomePageFragment.newInstance())
                .commitNow()
        }
    }

}
