package com.marshlandbiomes101.unity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.marshlandbiomes101.unity.databinding.UserFragmentBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


class UserFragment : Fragment() {

    private lateinit var binding: UserFragmentBinding
//    lateinit var dataStore: DataStore<Preferences>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = UserFragmentBinding.inflate(inflater, container, false)

        // Call fragment's xml elements to display user input from 'Register' page
        val displayedUsername = binding.displayUsername
        val displayedName = binding.displayName
        val displayedCity = binding.displayCity
        val displayedPhone = binding.displayPhone
        val displayedEmail = binding.displayEmail

        // To instantiate the datastore for use in a fragment, requireContext() is called
        val pref = requireContext().dataStore

        lifecycleScope.launch {
            // Make sure the keys are not empty, this will ensure that even if they were,
            // their values are replaced with 'None'
            val displayUsername = pref.data.map {
                it[KEY_USERNAME] ?: "None"
            }.first()

            val displayName = pref.data.map {
                it[KEY_NAME] ?: "None"
            }.first()

            val displayCity = pref.data.map {
                it[KEY_CITY] ?: "None"
            }.first()

            val displayPhone = pref.data.map {
                it[KEY_PHONE] ?: "None"
            }.first()

            val displayEmail = pref.data.map {
                it[KEY_EMAIL] ?: "None"
            }.first()

            // Values to display the changes in user
            displayedUsername.text = "@$displayUsername"
            displayedName.text = "Name: $displayName"
            displayedCity.text = "City: $displayCity"
            displayedPhone.text = "Phone: +$displayPhone"
            displayedEmail.text = "Email: $displayEmail"
        }

        // Make to return the fragment's contents, or an error happens
        return binding.root
    }
}

// UNUSED CODE / CONCEPTS
// ----------------------
//        backUserBtn.setOnClickListener() {
//            val fragmentManager: FragmentManager = parentFragmentManager
//            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
//
//            fragmentTransaction.addToBackStack(null);
//            fragmentTransaction.commit();
//        }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(binding.root, savedInstanceState)
//
////            println(displayedUsername)
////        }
//    }
//}

//
//        val displayUsername = binding.displayUsername
//            val EXAMPLE_COUNTER = intPreferencesKey("example_counter")
//            val exampleCounterFlow: Flow<Int> = context.dataStore.data
//                .map { preferences ->
//                    // No type safety.
//                    preferences[EXAMPLE_COUNTER] ?: 0
//                }

//            val displayName = binding.displayName
//            val displayCity = binding.displayCity
//            val displayPhone = binding.displayPhone
//            val displayEmail = binding.displayEmail

//        }

//    override fun onDestroyView() {
//        super.onDestroyView()
//    }
//        lifecycleScope.launch {
//            val displayedUsername = dataStore.data.map {
//                it[KEY_USERNAME] ?: ""
//            }.first()
//

//
//            displayUsername.text = buildString {
//                append("@")
//                append(displayedUsername)
//            }
//

//}

