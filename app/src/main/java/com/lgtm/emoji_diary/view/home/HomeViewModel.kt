package com.lgtm.emoji_diary.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lgtm.emoji_diary.data.Diary
import com.lgtm.emoji_diary.data.source.DiaryRepository
import com.lgtm.emoji_diary.utils.CalendarUtil
import com.lgtm.emoji_diary.view.edit.SimpleDate
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val diaryRepository: DiaryRepository,
): ViewModel() {

    private val _selectedDate: MutableLiveData<SimpleDate> = MutableLiveData(CalendarUtil.getCurrentSimpleDate())
    val selectedDate: LiveData<SimpleDate>
        get() = _selectedDate

    // val diaries: LiveData<List<Diary>>

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

}