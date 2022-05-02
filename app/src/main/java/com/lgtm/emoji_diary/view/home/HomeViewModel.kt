package com.lgtm.emoji_diary.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lgtm.emoji_diary.data.source.DiaryRepository
import com.lgtm.emoji_diary.view.edit.SimpleDate
import com.lgtm.emoji_diary.view.edit.getCurrentSimpleDate
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val diaryRepository: DiaryRepository,
): ViewModel() {

    private val _selectedDate: MutableLiveData<SimpleDate> = MutableLiveData(getCurrentSimpleDate())
    val selectedDate: LiveData<SimpleDate>
        get() = _selectedDate
}