package com.lgtm.emoji_diary.view.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.lgtm.emoji_diary.R
import com.lgtm.emoji_diary.databinding.FragmentDetailBinding
import com.lgtm.emoji_diary.delegate.viewBinding
import com.lgtm.emoji_diary.utils.EmojiStore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val binding: FragmentDetailBinding by viewBinding(FragmentDetailBinding::bind)

    private val viewModel: DetailViewModel by viewModels()

    private val args: DetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolBar()

        observeViewModel()

        Timber.d("Detail : ${args.diaryId}")
    }

    private fun initToolBar() = with(binding) {
        toolBar.inflateMenu(R.menu.menu_detail)
        toolBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_edit -> {
                    viewModel.onEvent(DetailDiaryEvent.EditDiary(args.diaryId))
                    true
                }
                R.id.action_remove -> {
                    viewModel.onEvent(DetailDiaryEvent.RemoveDiary(args.diaryId))
                    true
                }
                else -> { false }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadDiary(args.diaryId)
    }

    private fun observeViewModel() {
        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            binding.titleView.text = uiState.title
            binding.contentView.text = uiState.content
            binding.dateView.text = uiState.date
            binding.timeView.text = uiState.time
            binding.emojiView.setImageDrawable(EmojiStore.getEmojiDrawable(requireContext(), uiState.emojiId))

            if (uiState.loadErrorMessage.isNotEmpty()) {
                Toast.makeText(requireContext(), uiState.loadErrorMessage, Toast.LENGTH_LONG).show()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.eventFlow.collect { event ->
                    handleEvent(event)
                }
            }
        }
    }

    private fun handleEvent(event: DetailDiaryEvent) {
        when (event) {
            is DetailDiaryEvent.EditDiary -> {
                moveToEditPage(event.diaryId)
            }
            is DetailDiaryEvent.RemoveDiary -> {
                findNavController().popBackStack()
            }
        }
    }

    private fun moveToEditPage(diaryId: Long) {
        val action = DetailFragmentDirections.actionDetailFragmentToEditFragment(diaryId = diaryId)
        findNavController().navigate(action, )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_detail, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

}