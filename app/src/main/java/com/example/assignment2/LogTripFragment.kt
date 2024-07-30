package com.example.assignment2

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment

class LogTripFragment : Fragment() {

    private lateinit var tripDestination: EditText
    private lateinit var tripDate: EditText
    private lateinit var tripRating: Spinner
    private lateinit var tripTypeGroup: RadioGroup
    private lateinit var btnSave: Button
    private var listener: OnTripSavedListener? = null

    interface OnTripSavedListener {
        fun onTripSaved(trip: Trip)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnTripSavedListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnTripSavedListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_log_trip, container, false)

        tripDestination = view.findViewById(R.id.edit_trip_destination)
        tripDate = view.findViewById(R.id.edit_trip_date)
        tripRating = view.findViewById(R.id.spinner_trip_rating)
        tripTypeGroup = view.findViewById(R.id.radio_group_trip_type)
        btnSave = view.findViewById(R.id.button_save_trip)

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.rating_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            tripRating.adapter = adapter
        }

        btnSave.setOnClickListener {
            val destination = tripDestination.text.toString()
            val date = tripDate.text.toString()
            val rating = tripRating.selectedItem.toString()
            val tripTypeId = tripTypeGroup.checkedRadioButtonId
            val tripType = view.findViewById<RadioButton>(tripTypeId)?.text?.toString() ?: ""

            if (destination.isNotEmpty() && date.isNotEmpty() && rating.isNotEmpty() && tripType.isNotEmpty()) {
                val trip = Trip(destination, date, rating, tripType)
                listener?.onTripSaved(trip)
                clearInputFields()
            } else {
                Toast.makeText(requireContext(), "No data entered", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    private fun clearInputFields() {
        tripDestination.text.clear()
        tripDate.text.clear()
        tripRating.setSelection(0)
        tripTypeGroup.clearCheck()
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}
