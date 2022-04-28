package com.lgtm.emoji_diary.view.edit

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.lgtm.emoji_diary.R
import com.lgtm.emoji_diary.databinding.FragmentEditBinding
import com.lgtm.emoji_diary.delegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditFragment : Fragment(R.layout.fragment_edit) {

    private val binding: FragmentEditBinding by viewBinding(FragmentEditBinding::bind)

    private val viewModel: EditViewModel by viewModels()

    private val args: EditFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        setFragmentResults()

        setUiEventListeners()

        observeViewModel()
    }

    private fun initViews() {
        val diaryId = args.diaryId
        viewModel.onViewCreated(diaryId)
    }

    private fun setFragmentResults() {
        setFragmentResultListener("KeyDatePicker") { _, result ->
            result.getParcelable<SimpleDate>("selectedDate")?.let {
                viewModel.onEvent(EditDiaryEvent.DateChanged(it))
            }
        }

        setFragmentResultListener("KeyTimePicker") { _, result ->
            result.getParcelable<SimpleDate>("selectedTime")?.let {
                viewModel.onEvent(EditDiaryEvent.TimeChanged(it))
            }
        }
    }

    private fun setUiEventListeners() {
        binding.titleView.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

            override fun afterTextChanged(str: Editable) {
                viewModel.onEvent(EditDiaryEvent.TitleChanged(str.toString()))
            }
        })

        binding.contentView.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

            override fun afterTextChanged(str: Editable) {
                viewModel.onEvent(EditDiaryEvent.ContentChanged(str.toString()))
            }
        })

        binding.datePickerIcon.setOnClickListener {
            viewModel.onEvent(EditDiaryEvent.DatePickerClicked())
        }

        binding.timePickerIcon.setOnClickListener {
            viewModel.onEvent(EditDiaryEvent.TimePickerClicked())
        }

        binding.saveButton.setOnClickListener {
            viewModel.onEvent(EditDiaryEvent.Save)
        }
    }

    private fun observeViewModel() {
        viewModel.uiState.observe(viewLifecycleOwner, { uiState ->
            binding.datePickerView.text = uiState.date
            binding.timePickerView.text = uiState.time
        })

        lifecycleScope.launchWhenStarted {
            viewModel.datePickerClicked.collect {
                moveToDatePickerFragment(it)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.timePickerClicked.collect {
                moveToTimePickerFragment(it)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.saveAndQuit.collect {
                // moveToDetailFragment()
                findNavController().popBackStack()
            }
        }
    }

    private fun moveToDatePickerFragment(currentDate: SimpleDate) {
        val action = EditFragmentDirections.actionEditFragmentToDatePickerFragment(currentDate)
        findNavController().navigate(action)
    }

    private fun moveToTimePickerFragment(currentDate: SimpleDate) {
        val action = EditFragmentDirections.actionEditFragmentToTimePickerFragment(currentDate)
        findNavController().navigate(action)
    }

//    private fun moveToDetailFragment() {
//
//    }

}