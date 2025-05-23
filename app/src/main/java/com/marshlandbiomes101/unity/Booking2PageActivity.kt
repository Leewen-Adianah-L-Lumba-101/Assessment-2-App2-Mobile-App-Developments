@file:Suppress("DEPRECATION") // Depreciated function onBackPressed() to return to previous activity

package com.marshlandbiomes101.unity

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import com.marshlandbiomes101.unity.databinding.Booking2PageBinding
import kotlinx.coroutines.launch
import kotlin.random.Random

// Mockup unique code to display for the ticket!
val ticketnumber1 = Random.nextInt(9).toString()
val ticketnumber2 = Random.nextInt(9).toString()
val ticketnumber3 = Random.nextInt(9).toString()
var uniqueCode = buildString {
        append("J")
        append(ticketnumber1)
        append("S")
        append(ticketnumber2)
        append("T")
        append(ticketnumber3)
    }

// Variables to store the displayed values for the booked ticket
val KEY_PLACE = stringPreferencesKey("display_place")
val KEY_DATE = stringPreferencesKey("display_date")
val KEY_TIMINGS = stringPreferencesKey("display_timings")
val KEY_DOORS = stringPreferencesKey("display_doors")
val KEY_CODE = stringPreferencesKey("display_code")

class Booking2PageActivity : AppCompatActivity() {
    private lateinit var binding: Booking2PageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = Booking2PageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val paymentBtn = binding.paymentBtn
        val bookingBack = binding.backBookingBtn

        // Values to display the data
        val noOfPeople = binding.displayPeople
        val priceTag = binding.displayPrice
        val placeName = binding.displayPlaceName
        val date = binding.displayActiveDate
        val location = binding.displayActiveVenue
        val timings = binding.displayActiveTimings
        val image = binding.displayImagePlace

        // From the .putExtra() function in BookingPageActivity, the variable 'message' stores the
        // value from the key/name called 'message'. It receives what has been sent from the previous
        // activity. I kept it the same name to prevent confusion.
        val message = intent.getStringExtra("message")

        // Create variables to store the data, it changes throughout my filtering logic so I only
        // need to call the datastore edit only once.
        var drawable: Drawable? = ResourcesCompat.getDrawable(resources, R.drawable.forum_karlin, null)
        var ticketName = ""
        var ticketDate = ""
        var ticketTimings = ""
        var ticketDoors = ""
        placeName.text = message

        // Assign appropriate information about the venues in each text, and display them for the user
        // Image also changes
        when (message) {
            "Forum Karlin" -> {
                priceTag.text = "35€"
                date.text = "Monday, March 10th 2025"
                noOfPeople.text = "96"
                location.text = "Prague, Czechia"
                timings.text = "8:45pm - 10:20pm"
                drawable = ResourcesCompat.getDrawable(resources, R.drawable.forum_karlin, null)

                ticketName = "PRAGUE, FORUM KARLIN"
                ticketDate = "MONDAY, MARCH 10 2025"
                ticketTimings = "8:45PM - 10:20PM"
                ticketDoors = "7:00PM"
            }

            "Raiffeisen Halle im Gasometer" -> {
                priceTag.text = "25€"
                date.text = "Sunday, March 9th 2025"
                noOfPeople.text = "53"
                location.text = "Vienna, Austria"
                timings.text = "8:45pm - 10:20pm"
                drawable = ResourcesCompat.getDrawable(resources, R.drawable.halle_gasometer, null)

                ticketName = "VIENNA, RAIFFEISEN HALLE IM GASOMETER"
                ticketDate = "SUNDAY, MARCH 9 2025"
                ticketTimings = "8:45PM - 10:20PM"
                ticketDoors = "7:00PM"
            }

            "ZOOM Frankfurt" -> {
                priceTag.text = "37€"
                date.text = "Friday, March 7th 2025"
                noOfPeople.text = "104"
                location.text = "Frankfurt, Germany"
                timings.text = "8:45pm – 10:15pm"
                drawable = ResourcesCompat.getDrawable(resources, R.drawable.zoom_frankfurt, null)

                ticketName = "FRANKFURT, ZOOM FRANKFURT"
                ticketDate = "FRIDAY, MARCH 7 2025"
                ticketTimings = "8:45PM - 10:15PM"
                ticketDoors = "6:00PM"
            }

            "Klub Stodoła" -> {
                priceTag.text = "25€"
                date.text = "Wednesday, March 12th 2025"
                noOfPeople.text = "74"
                location.text = "Warsaw, Poland"
                timings.text = "8:45pm - 10:25pm"
                drawable = ResourcesCompat.getDrawable(resources, R.drawable.klub_stodola, null)

                ticketName = "WARSAW, KLUB STODOŁA"
                ticketDate = "WEDNESDAY, MARCH 12 2025"
                ticketTimings = "8:45PM - 10:25PM"
                ticketDoors = "6:00PM"
            }
        }

        image.setImageDrawable(drawable)

        // Storing all these in different keys so the booked ticket details can be displayed in the
        // HomeFragment. This is done by accessing the datastore's keys because it's publicly available.
        lifecycleScope.launch {
            dataStore.edit { prefs ->
                prefs[KEY_PLACE] = ticketName
                prefs[KEY_DATE] = ticketDate
                prefs[KEY_TIMINGS] = ticketTimings
                prefs[KEY_DOORS] = ticketDoors
                prefs[KEY_CODE] = uniqueCode
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // When user presses payment button, go to the fragmented pages
        paymentBtn.setOnClickListener() {
            val intent = Intent(this@Booking2PageActivity, HomePageActivity::class.java)
            Toast.makeText(this, "Ticket booked!", Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }

        // When user presses back button, return to BookingPageActivity
        bookingBack.setOnClickListener() {
//            val intent = Intent(this@Booking2PageActivity, BookingPageActivity::class.java)
//            startActivity(intent)
            onBackPressed()
        }
    }
}

// UNUSED CODE / CONCEPTS
// ----------------------
//data class venueItem(
//    val name: String,
//    val date: String,
//    val image: String,
//    val price: String,
//    val location: String,
//    val timings: String,
//    val people: Int
//)

// CODE COPIED FROM THE UX10 DESIGN
//val adapter = ItemAdapter(sampleImageList) { item ->
//    println(item.name)
//}
//
//
//val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
//
//
//recyclerView.layoutManager = LinearLayoutManager(this)
//recyclerView.adapter = adapter
//
//
////recyclerView.layoutManager = GridLayoutManager(this, 2)
////recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
//
//}
//}
//
//class ItemAdapter(private val data: List<ImageItem>, private val listener: (item: ImageItem) -> Unit) :
//    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
//
//        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return ItemViewHolder(binding)
//
//    }
//
//    override fun getItemCount(): Int {
//        return data.count()
//    }
//
//    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
//        holder.bind(data[position])
//    }
//
//    inner class ItemViewHolder(val binding: ItemProductBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//        fun bind(itemData: ImageItem) {
//            println(itemData.url)
//
//            binding.itemText.text = itemData.name
//            Glide.with(binding.image.context)
//                .load(itemData.url)
//                .into(binding.image)
//
//            binding.root.setOnClickListener {
//                listener(itemData)
//            }
//        }
//
//    }
//}