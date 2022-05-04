package com.lgtm.emoji_diary.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lgtm.emoji_diary.data.source.DiaryRepository
import com.lgtm.emoji_diary.view.edit.SimpleDate
import com.lgtm.emoji_diary.view.edit.getCurrentSimpleDate
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val diaryRepository: DiaryRepository,
): ViewModel() {

    private val _selectedDate: MutableLiveData<SimpleDate> = MutableLiveData(getCurrentSimpleDate())
    val selectedDate: LiveData<SimpleDate>
        get() = _selectedDate

    private val _diaryClicked = MutableSharedFlow<Long>()
    val diaryClicked = _diaryClicked.asSharedFlow()

    private val _emptyDiaryClicked = MutableSharedFlow<SimpleDate>()
    val emptyDiaryClicked = _emptyDiaryClicked.asSharedFlow()

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.ClickCalendarDay -> {
                if (event.diaryId != -1L) {
                    viewModelScope.launch {
                        _diaryClicked.emit(event.diaryId)
                    }
                } else {
                    viewModelScope.launch {
                        _emptyDiaryClicked.emit(event.date)
                    }
                }
            }
            is HomeEvent.ClickTimelineItem -> {
                viewModelScope.launch {
                    _diaryClicked.emit(event.diaryId)
                }
            }
        }
    }
}