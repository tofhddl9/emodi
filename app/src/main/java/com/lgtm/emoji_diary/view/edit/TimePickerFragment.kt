package com.lgtm.emoji_diary.view.edit

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.navArgs

class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    private val args: TimePickerFragmentArgs by navArgs()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val date = args.date

        return TimePickerDialog(requireContext(), this, date.hourOfDay, date.minute, true)
    }

    override fun onTimeSet(view: TimePicker?, hour: Int, minute: Int) {
        val selectedDate = SimpleDate(hourOfDay = hour, minute = minute)
        val bundle = Bundle().apply {
            putParcelable("selectedTime", selectedDate)
        }
        setFragmentResult("KeyTimePicker", bundle)
    }

    companion object {
        fun newInstance() = TimePickerFragment()
    }
}