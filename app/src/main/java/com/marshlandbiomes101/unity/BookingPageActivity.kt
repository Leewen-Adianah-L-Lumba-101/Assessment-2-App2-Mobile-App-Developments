package com.marshlandbiomes101.unity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import com.marshlandbiomes101.unity.databinding.BookingPageBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

// Create variables to save user's name and phone number, displayed on UserFragment page
val KEY_NAME = stringPreferencesKey("user_name_name")
val KEY_PHONE = stringPreferencesKey("user_phone")

class BookingPageActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: BookingPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = BookingPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Create variables for the next page button and the spinner that will decide the four different
        // venues the user can book from
        val nextBtn = binding.nextBtn
        val spinner : Spinner = binding.spinnerBar
        // This assigns an option for each spinner, to filter through the venues
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            listOf("Prague, Czechia", "Vienna, Austria", "Frankfurt, Germany", "Warsaw, Poland")
        )

        // Variables to obtain the input from the EditText to display on UserFragment
        val name = binding.bookName
        val phone = binding.userPhone

        // resultsLine to warn user of errors
        val resultsLine = binding.resultsLineBooking
        var check = false
        var check2 = false

        // This is the logic that should run whenever the user presses a spinner option, for every option
        // a 'message' from the Activity is sent using .putExtra, it sends information from the current activity
        // to the specified activity (see notes on Booking2PageActivity)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val intent = Intent(this@BookingPageActivity, Booking2PageActivity::class.java)

                when(val position: Int = spinner.selectedItemPosition) {
                    0 -> {
                        intent.putExtra("message","Forum Karlin")
                        check2 = true // 'check2' is to make sure the next activity doesn't immediately start
                        if (check) { // as the 'check' (to ensure all fields have input) needs to be checked first
                            startActivity(intent)
                            Toast.makeText(this@BookingPageActivity, "Venue found!", Toast.LENGTH_SHORT).show()
                        }
                    }

                    1 -> {
                        intent.putExtra("message","Raiffeisen Halle im Gasometer")
                        check2 = true
                        if (check) {
                            startActivity(intent)
                            Toast.makeText(this@BookingPageActivity, "Venue found!", Toast.LENGTH_SHORT).show()
                        }
                    }

                    2 -> {
                        intent.putExtra("message","ZOOM Frankfurt")
                        check2 = true
                        if (check) {
                            startActivity(intent)
                            Toast.makeText(this@BookingPageActivity, "Venue found!", Toast.LENGTH_SHORT).show()
                        }
                    }

                    3 -> {
                        intent.putExtra("message","Klub Stodoła")
                        check2 = true
                        if (check) {
                            startActivity(intent)
                            Toast.makeText(this@BookingPageActivity, "Venue found!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        // Same logic as the RegisterPageActivity, for checking empty EditText fields
        nextBtn.setOnClickListener {
            lifecycleScope.launch {

                dataStore.edit { prefs ->
                    prefs[KEY_NAME] = name.text.toString()
                    prefs[KEY_PHONE] = phone.text.toString()
                }

                if (name.text.toString().isEmpty() && phone.text.toString().isEmpty()) {
                    resultsLine.visibility = View.VISIBLE
                    resultsLine.text = "Invalid credentials."

                } else if (name.text.toString().isEmpty()) {
                    resultsLine.visibility = View.VISIBLE
                    resultsLine.text = "Invalid name."

                } else if (phone.text.toString().isEmpty()) {
                    resultsLine.visibility = View.VISIBLE
                    resultsLine.text = "Invalid phone."

                } else {
                    resultsLine.visibility = View.INVISIBLE
                    check = true
                }
            }

            // if the check is true start next page
            if (check) {
                val intent = Intent(this@BookingPageActivity, Booking2PageActivity::class.java)
                startActivity(intent)
            }
        }

        // Clears booking input fields
        lifecycleScope.launch {

            val empty = ""

            name.setText(empty)
            phone.setText(empty)
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

}