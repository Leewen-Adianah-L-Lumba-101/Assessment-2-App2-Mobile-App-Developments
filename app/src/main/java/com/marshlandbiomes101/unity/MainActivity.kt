package com.marshlandbiomes101.unity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.marshlandbiomes101.unity.databinding.ActivityMainBinding
import android.widget.Toast
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class MainActivity : AppCompatActivity() {
    // Create binding class to access elements of xml file quicker
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Delay app launch from happening to load in the custom splash screen first
        Thread.sleep(800)
        installSplashScreen()

        binding = ActivityMainBinding.inflate(layoutInflater)

        // .root is the xml file itself
        setContentView(binding.root)
        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val welcomeBtn = binding.welcomeBtn

        // When user presses the 'next' welcome button, an intent will start, launching the specified activity
        // This is the next page of the app
        welcomeBtn.setOnClickListener {
            val intent = Intent(this@MainActivity, RegisterPageActivity::class.java)
            Toast.makeText(this, "Please enter your details :)", Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }
    }
}