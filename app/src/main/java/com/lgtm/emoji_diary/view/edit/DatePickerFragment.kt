package com.lgtm.emoji_diary.view.edit

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.navArgs

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    private val args: DatePickerFragmentArgs by navArgs()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val date = args.date

        return DatePickerDialog(requireContext(), this, date.year, date.month, date.day)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
        val selectedDate = SimpleDate(year = year, month = month, day = day)
        val bundle = Bundle().apply {
            putParcelable("selectedDate", selectedDate)
        }
        setFragmentResult("KeyDatePicker", bundle)
    }

    companion object {
        fun newInstance() = DatePickerFragment()
    }
}