package com.lgtm.emoji_diary.view.edit

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.lgtm.emoji_diary.R

class EditFragment : Fragment(R.layout.fragment_edit) {

    val viewModel: EditViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}