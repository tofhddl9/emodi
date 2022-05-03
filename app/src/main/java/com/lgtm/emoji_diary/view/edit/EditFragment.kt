package com.lgtm.emoji_diary.view.edit

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.lgtm.emoji_diary.R
import com.lgtm.emoji_diary.databinding.FragmentEditBinding
import com.lgtm.emoji_diary.delegate.viewBinding
import com.lgtm.emoji_diary.utils.EmojiStore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import timber.log.Timber

@AndroidEntryPoint
class EditFragment : Fragment(R.layout.fragment_edit) {

    private val binding: FragmentEditBinding by viewBinding(FragmentEditBinding::bind)

    private val viewModel: EditViewModel by viewModels()

    private val args: EditFragmentArgs by navArgs()

//    private val titleViewTextWatcher: TextWatcher = object: TextWatcher {
//        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
//
//        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
//
//        override fun afterTextChanged(str: Editable) {
//            viewModel.onEvent(EditDiaryEvent.TitleChanged(str.toString()))
//        }
//    }
//
//    private val contentViewTextWatcher: TextWatcher = object: TextWatcher {
//        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
//
//        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
//
//        override fun afterTextChanged(str: Editable) {
//            viewModel.onEvent(EditDiaryEvent.ContentChanged(str.toString()))
//        }
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        setFragmentResults()

        setUiEventListeners()

        observeViewModel()

        Timber.d("Edit : ${args.diaryId}")
    }

    private fun initViews() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = viewModel

        viewModel.loadDiary(args.diaryId)
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

        setFragmentResultListener(EditFragmentResult.KEY_EMOJI_ON_CLICK) { _, result ->
            val emojiId = result.getLong(EditFragmentResult.KEY_EMOJI_ID)
            viewModel.onEvent(EditDiaryEvent.EmojiChanged(emojiId))
        }
    }

    private fun setUiEventListeners() {
        binding.emojiPickerView.setOnClickListener {
            // viewModel.onEvent(EditDiaryEvent.EmojiPickerClicked)
            moveToEmojiPickerFragment()
        }

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
//            binding.titleView.removeTextChangedListener(titleViewTextWatcher)
//            binding.titleView.setText(uiState.title)
//            binding.titleView.setSelection(binding.titleView.text.length)
//            binding.titleView.addTextChangedListener(titleViewTextWatcher)
//
//            binding.contentView.removeTextChangedListener(contentViewTextWatcher)
//            binding.contentView.setText(uiState.content)
//            binding.contentView.setSelection(binding.contentView.text.length)
//            binding.contentView.addTextChangedListener(contentViewTextWatcher)
            binding.emojiPickerView.setImageDrawable(EmojiStore.getEmojiDrawable(requireContext(), uiState.emojiId))
            binding.datePickerView.text = uiState.date
            binding.timePickerView.text = uiState.time
        })

        lifecycleScope.launchWhenStarted {
            viewModel.emojiPickerClicked.collect {
                moveToEmojiPickerFragment()
            }
        }

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

    private fun moveToEmojiPickerFragment() {
        val action = EditFragmentDirections.actionEditFragmentToEmojiPickerFragment()
        findNavController().navigate(action)
    }

    private fun moveToDatePickerFragment(currentDate: SimpleDate) {
        val action = EditFragmentDirections.actionEditFragmentToDatePickerFragment(currentDate)
        findNavController().navigate(action)
    }

    private fun moveToTimePickerFragment(currentDate: SimpleDate) {
        val action = EditFragmentDirections.actionEditFragmentToTimePickerFragment(currentDate)
        findNavController().navigate(action)
    }

    private fun moveToDetailFragment() {
        // val action = EditFragmentDirections.
    }

}