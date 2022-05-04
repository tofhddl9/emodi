package com.lgtm.emoji_diary.view.home.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lgtm.emoji_diary.data.Result
import com.lgtm.emoji_diary.data.source.DiaryRepository
import com.lgtm.emoji_diary.view.edit.timeInMillisToSimpleDate
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val diaryRepository: DiaryRepository
) : ViewModel() {

    private val _uiState = MutableLiveData(CalendarUiState())
    val uiState: LiveData<CalendarUiState>
        get() = _uiState

    fun getDiaries() {
        viewModelScope.launch {
            diaryRepository.getDiaries().collect { result ->
                when (result) {
                    is Result.Success -> {
                        val emojiMap = result.data.map {
                            timeInMillisToSimpleDate(it.date).copy(
                                dayOfWeek = 1,
                                hourOfDay = 0,
                                minute = 0
                            ) to it
                        }.toMap()

                        _uiState.value = _uiState.value?.copy(
                            calendarEmojiMap = emojiMap
                        )
                    }
                    is Result.Error -> {
                        _uiState.value = _uiState.value?.copy(
                            loadErrorMessage = "캘린더에 일기장을 불러오는데 실패했습니다."
                        )
                    }
                }
            }
        }
    }

    fun onEvent(event: DiaryCalendarEvent) {
        when (event) {
            is DiaryCalendarEvent.ScrollMonth -> {
                _uiState.value = _uiState.value?.copy(
                    year = event.year,
                    month = event.month,
                )
            }
            is DiaryCalendarEvent.ClickItem -> {

            }
        }
    }

}