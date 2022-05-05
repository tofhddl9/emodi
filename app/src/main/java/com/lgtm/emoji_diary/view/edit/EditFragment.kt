package com.lgtm.emoji_diary.view.edit

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.lgtm.emoji_diary.R
import com.lgtm.emoji_diary.databinding.FragmentEditBinding
import com.lgtm.emoji_diary.delegate.viewBinding
import com.lgtm.emoji_diary.utils.EmojiStore
import com.lgtm.emoji_diary.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

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

        Timber.d("Edit : ${args.diaryId}")
    }

    private fun initViews() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = viewModel

        viewModel.loadDiary(args.diaryId, args.date)
    }

    private fun setFragmentResults() {
        setFragmentResultListener(EditFragmentResult.KEY_DATE_PICKER) { _, result ->
            result.getParcelable<SimpleDate>(EditFragmentResult.KEY_SELECTED_DATE)?.let {
                viewModel.onEvent(EditDiaryEvent.DateChanged(it))
            }
        }

        setFragmentResultListener(EditFragmentResult.KEY_TIME_PICKER) { _, result ->
            result.getParcelable<SimpleDate>(EditFragmentResult.KEY_SELECTED_TIME)?.let {
                viewModel.onEvent(EditDiaryEvent.TimeChanged(it))
            }
        }

        setFragmentResultListener(EditFragmentResult.KEY_EMOJI_PICKER) { _, result ->
            val emojiId = result.getLong(EditFragmentResult.KEY_EMOJI_ID)
            viewModel.onEvent(EditDiaryEvent.EmojiChanged(emojiId))
        }
    }

    private fun setUiEventListeners() {
        binding.emojiPickerView.setOnClickListener {
            viewModel.onEvent(EditDiaryEvent.EmojiPickerClicked)
        }

        binding.datePickerIcon.setOnClickListener {
            viewModel.onEvent(EditDiaryEvent.DatePickerClicked())
        }

        binding.timePickerIcon.setOnClickListener {
            viewModel.onEvent(EditDiaryEvent.TimePickerClicked())
        }

        binding.imagePickerIcon.setOnClickListener {
            viewModel.onEvent(EditDiaryEvent.ImagePickerClicked)
        }

        binding.saveButton.setOnClickListener {
            viewModel.onEvent(EditDiaryEvent.Save)
        }
    }

    private fun observeViewModel() {
        viewModel.uiState.observe(viewLifecycleOwner, { uiState ->
            binding.emojiPickerView.setImageDrawable(EmojiStore.getEmojiDrawable(requireContext(), uiState.emojiId))
            binding.datePickerView.text = uiState.date
            binding.timePickerView.text = uiState.time

            if (!uiState.datePickerEnabled) {
                binding.datePickerIcon.setOnClickListener(null)
                binding.datePickerView.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                binding.datePickerIcon.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.black))
            }

            if (uiState.emojiErrorMessage != null) {
                SimpleTooltip.Builder(requireContext())
                    .anchorView(binding.emojiPickerView)
                    .text("이날의 감정을 선택해주세요.")
                    .gravity(Gravity.BOTTOM)
                    .animated(true)
                    .transparentOverlay(false)
                    .build()
                    .show()
            }
        })

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.emojiPickerClicked.collect {
                    moveToEmojiPickerFragment()
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.datePickerClicked.collect {
                    moveToDatePickerFragment(it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.timePickerClicked.collect {
                    moveToTimePickerFragment(it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.imagePickerClicked.collect {
                    showSnackBar("아직 지원하지 않는 기능입니다 :(")
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.saveAndQuit.collect {
                    findNavController().popBackStack()
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.validationEvents.collect {
                    showSnackBar("고생 많았어요 :)", Snackbar.LENGTH_LONG)
                }
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

}