package com.lgtm.emoji_diary.view.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.lgtm.emoji_diary.R
import com.lgtm.emoji_diary.databinding.FragmentDetailBinding
import com.lgtm.emoji_diary.delegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val binding: FragmentDetailBinding by viewBinding(FragmentDetailBinding::bind)

    private val viewModel: DetailViewModel by viewModels()

    private val args: DetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()

        viewModel.onViewCreated(args.diaryId)
    }

    private fun observeViewModel() {
        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            binding.titleView.text = uiState.title
            binding.contentView.text = uiState.content
            binding.dateView.text = uiState.date
            binding.timeView.text = uiState.time

            if (uiState.loadErrorMessage.isNotEmpty()) {
                Toast.makeText(requireContext(), "에러", Toast.LENGTH_LONG).show()
            }
        }
    }

}