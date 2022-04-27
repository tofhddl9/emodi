package com.lgtm.emoji_diary.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lgtm.emoji_diary.data.Diary
import com.lgtm.emoji_diary.utils.CalendarUtil
import com.lgtm.emoji_diary.view.edit.SimpleDate
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(): ViewModel() {

    private val _selectedDate: MutableLiveData<SimpleDate> = MutableLiveData(CalendarUtil.getCurrentSimpleDate())
    val selectedDate: LiveData<SimpleDate>
        get() = _selectedDate

    private val _diaries: MutableLiveData<List<Diary>> = MutableLiveData(listOf())
    val diaries: LiveData<List<Diary>>
        get() = _diaries

}