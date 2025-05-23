package com.marshlandbiomes101.unity

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.lifecycleScope
import com.marshlandbiomes101.unity.databinding.HomeFragmentBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.map


class HomeFragment : Fragment() {

    private lateinit var binding: HomeFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(inflater, container, false)

        // Call datastore with requireContext() to reference it for this fragment
        val pref = requireContext().dataStore

        // Create TextView variables to change their contents
        val ticketPlaceName = binding.displayTicketPlace
        val ticketDate = binding.displayTicketDate
        val ticketTimings = binding.displayTicketTimings
        val ticketCode = binding.displayTicketCode
        val ticketImage = binding.displayTicketImage

        lifecycleScope.launch {
            val displayTicketPlace = pref.data.map {
                it[KEY_PLACE] ?: "None"
            }.first()

            val displayTicketDate = pref.data.map {
                it[KEY_DATE] ?: "None"
            }.first()

            val displayTicketTimings = pref.data.map {
                it[KEY_TIMINGS] ?: "None"
            }.first()

            val displayTicketDoors = pref.data.map {
                it[KEY_DOORS] ?: "None"
            }.first()

            val displayTicketCode = pref.data.map {
                it[KEY_CODE] ?: "None"
            }.first()

            // Display the booking information by replacing the text in the TextView variables
            ticketPlaceName.text = "$displayTicketPlace"
            ticketDate.text = "$displayTicketDate"
            ticketTimings.text = buildString {
                append("TIMINGS: $displayTicketTimings ")
                append("DOORS: $displayTicketDoors")
            }
            ticketCode.text = "$displayTicketCode"

            // Create a drawable variable to make sure the images in the ticket change according to the place.
            // But for now, the variable is given a placeholder image
            var drawable: Drawable? = ResourcesCompat.getDrawable(resources, R.drawable.forum_karlin, null)

            // Make sure the variable holding the image is changed when the place changes
            when (displayTicketPlace) {
                "PRAGUE, FORUM KARLIN" -> drawable = ResourcesCompat.getDrawable(resources, R.drawable.forum_karlin, null)
                "VIENNA, RAIFFEISEN HALLE IM GASOMETER" -> drawable = ResourcesCompat.getDrawable(resources, R.drawable.halle_gasometer, null)
                "FRANKFURT, ZOOM FRANKFURT" -> drawable = ResourcesCompat.getDrawable(resources, R.drawable.zoom_frankfurt, null)
                "WARSAW, KLUB STODOŁA" -> drawable = ResourcesCompat.getDrawable(resources, R.drawable.klub_stodola, null)
            }

            // Change the ImageView src
            ticketImage.setImageDrawable(drawable)
        }
        return binding.root
    }
}

