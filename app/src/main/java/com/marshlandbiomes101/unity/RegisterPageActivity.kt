package com.marshlandbiomes101.unity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.marshlandbiomes101.unity.databinding.RegisterPageBinding
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

// Create the datastore, where all important keys and values are going to be saved into
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_input")

// Variables for displaying the user's details on their profile page
val KEY_EMAIL = stringPreferencesKey("user_email")
val KEY_USERNAME = stringPreferencesKey("user_name")
val KEY_PASSWORD = stringPreferencesKey("pass_word")
val KEY_CITY = stringPreferencesKey("user_city")

class RegisterPageActivity : AppCompatActivity() {
    private lateinit var binding: RegisterPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = RegisterPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Buttons to enter values and resultsLine to display error messages
        val registerBtn = binding.registerBtn
        val resultsLine = binding.resultsLine

        // Instantiate the EditText fields to obtain the user's input
        val email = binding.userEmail
        val username = binding.userUsername
        val password = binding.userPassword
        val city = binding.userCity

        // Check to ensure that all EditText fields are filled
        var check = false

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Clear register input fields
        lifecycleScope.launch {
            val empty = ""
            city.setText(empty)
            email.setText(empty)
            username.setText(empty)
            password.setText(empty)
        }

        // When user clicks the register button, makes sure the fields are not empty
        // and NULL is not being returned to the datastore
        registerBtn.setOnClickListener {
            lifecycleScope.launch {

                // Writing to datastore
                dataStore.edit { prefs ->
                    prefs[KEY_EMAIL] = email.text.toString()
                    prefs[KEY_USERNAME] = username.text.toString()
                    prefs[KEY_PASSWORD] = password.text.toString()
                    prefs[KEY_CITY] = city.text.toString()
                }

                // resultsLine changes when user leaves certain fields blank
                if (email.text.toString().isEmpty() && username.text.toString().isEmpty() && password.text.toString().isEmpty() && city.text.toString().isEmpty()) {
                    resultsLine.visibility = View.VISIBLE
                    resultsLine.text = "Invalid credentials."

                } else if (email.text.toString().isEmpty()) {
                    resultsLine.visibility = View.VISIBLE
                    resultsLine.text = "Invalid email."

                } else if (username.text.toString().isEmpty()) {
                    resultsLine.visibility = View.VISIBLE
                    resultsLine.text = "Invalid username."

                } else if (password.text.toString().isEmpty()) {
                    resultsLine.visibility = View.VISIBLE
                    resultsLine.text = "Invalid password."

                } else if (city.text.toString().isEmpty()) {
                    resultsLine.visibility = View.VISIBLE
                    resultsLine.text = "Invalid city."

                } else {
                    resultsLine.visibility = View.INVISIBLE
                    check = true
                }
            }

//            val userNameFlow: Flow<String> = dataStore.data.map {
//                it[KEY_USERNAME] ?: ""
//            }

            // Once all fields are given input, the if condition finds out if the check variable has
            // the 'true' boolean, then it starts the specified activity (next page) alongside an okay message.
            if (check) {
                val intent = Intent(this@RegisterPageActivity, BookingPageActivity::class.java)
                Toast.makeText(this, "Select a venue!", Toast.LENGTH_SHORT).show()
                startActivity(intent)
            }
        }
    }
}
