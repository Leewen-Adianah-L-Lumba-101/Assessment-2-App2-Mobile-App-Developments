package com.marshlandbiomes101.unity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.marshlandbiomes101.unity.databinding.HomePageBinding

class HomePageActivity : AppCompatActivity() {
    private lateinit var binding: HomePageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = HomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Bind to the bottom navigation bar to assign fragments to the different icons
        // Allowing the user to 'switch' between views
        binding.bottomNavBar.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.homeIcon -> replaceFragment(HomeFragment())
                R.id.starIcon -> replaceFragment(StarredFragment())
                R.id.bellIcon -> replaceFragment(NotificationFragment())
                R.id.userIcon -> replaceFragment(UserFragment())

                else -> {
                }
            }
            true
        }
    }

    // Function that calls on the fragment manager to switch between tabs aka fragments
    private fun replaceFragment(fragment : Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        // replaces the view in the frame_layout widget
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }
}