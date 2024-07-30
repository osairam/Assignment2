package com.example.assignment2

import android.app.Dialog
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class ViewSummaryFragment : DialogFragment() {

    companion object {
        private const val ARG_TRIPS = "trips"

        fun newInstance(trips: List<Trip>): ViewSummaryFragment {
            val fragment = ViewSummaryFragment()
            val args = Bundle()
            args.putParcelableArrayList(ARG_TRIPS, ArrayList(trips))
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val trips = arguments?.getParcelableArrayList<Trip>(ARG_TRIPS) ?: emptyList<Trip>()
            val tripSummaries = trips.map { "${it.destination} on ${it.date} - ${it.rating} (${it.tripType})" }

            val builder = AlertDialog.Builder(it)
            builder.setTitle("Trips")
                .setAdapter(ArrayAdapter(it, android.R.layout.simple_list_item_1, tripSummaries)) { _, _ -> }
                .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
