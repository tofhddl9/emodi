package com.lgtm.emoji_diary.view.edit.picker

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.lgtm.emoji_diary.databinding.DialogFragmentEmojiBinding
import com.lgtm.emoji_diary.view.edit.EditFragmentResult
import com.lgtm.emoji_diary.widgets.EmojiPickerAdapter

class EmojiPickerFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val binding = DialogFragmentEmojiBinding.inflate(layoutInflater)
        with(binding) {
            emojiList.layoutManager = GridLayoutManager(requireContext(), 3)
            emojiList.adapter = EmojiPickerAdapter(::onItemClick)
        }

        val dialog = builder.setView(binding.root).create()

        return dialog
    }

    private fun onItemClick(emojiId: Long) {
        setFragmentResult(
            EditFragmentResult.KEY_EMOJI_ON_CLICK,
            bundleOf(EditFragmentResult.KEY_EMOJI_ID to emojiId)
        )

        findNavController().popBackStack()
    }

}