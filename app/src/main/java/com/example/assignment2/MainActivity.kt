package com.example.assignment2

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity(), LogTripFragment.OnTripSavedListener {

    private lateinit var logTripFragment: Fragment
    private lateinit var viewSummaryFragment: ViewSummaryFragment
    private val trips = mutableListOf<Trip>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        logTripFragment = LogTripFragment()
        viewSummaryFragment = ViewSummaryFragment()

        val btnLogTrip: Button = findViewById(R.id.btn_log_trip)
        val btnViewSummary: Button = findViewById(R.id.btn_view_summary)

        btnLogTrip.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, logTripFragment)
                .addToBackStack(null)
                .commit()
        }

        btnViewSummary.setOnClickListener {
            val fragment = ViewSummaryFragment.newInstance(trips)
            fragment.show(supportFragmentManager, "ViewSummaryFragment")
        }
    }

    override fun onTripSaved(trip: Trip) {
        trips.add(trip)
        Toast.makeText(this, "Trip Saved: ${trip.destination}", Toast.LENGTH_SHORT).show()
    }
}
