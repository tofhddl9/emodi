package com.lgtm.emodi.view.edit.picker

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.navArgs
import com.lgtm.emodi.R
import com.lgtm.emodi.data.SimpleDate
import com.lgtm.emodi.view.edit.EditFragmentResult

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    private val args: DatePickerFragmentArgs by navArgs()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val date = args.date
        return DatePickerDialog(requireContext(), R.style.DialogTheme, this, date.year, date.month - 1, date.day)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
        val selectedDate = SimpleDate(year = year, month = month + 1, day = day)
        val bundle = Bundle().apply {
            putParcelable(EditFragmentResult.KEY_SELECTED_DATE, selectedDate)
        }
        setFragmentResult(EditFragmentResult.KEY_DATE_PICKER, bundle)
    }

    companion object {
        fun newInstance() = DatePickerFragment()
    }
}