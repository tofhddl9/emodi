package com.lgtm.emoji_diary.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lgtm.emoji_diary.data.source.DiaryRepository
import com.lgtm.emoji_diary.data.Result
import com.lgtm.emoji_diary.data.asDateFormat
import com.lgtm.emoji_diary.data.asTimeFormat
import com.lgtm.emoji_diary.data.timeInMillisToSimpleDate
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: DiaryRepository
) : ViewModel() {

    private val _uiState = MutableLiveData(DetailUiState())
    val uiState: LiveData<DetailUiState>
        get() = _uiState

    private val _eventFlow = MutableSharedFlow<DetailDiaryEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun loadDiary(diaryId: Long) {
        if (diaryId == -1L) {
            _uiState.value = _uiState.value?.copy(loadErrorMessage = "일기를 불러오는데 실패했습니다.")
        } else {
            viewModelScope.launch {
                val result = repository.getDiary(diaryId)
                when (result) {
                    is Result.Success -> {
                        val date = timeInMillisToSimpleDate(result.data.date)
                        _uiState.value = _uiState.value?.copy(
                            title = result.data.title,
                            content = result.data.content,
                            date = date.asDateFormat(),
                            time = date.asTimeFormat(),
                            emojiId = result.data.emojiId,
                            loadErrorMessage = "",
                        )
                    }
                    is Result.Error -> {
                        _uiState.value = _uiState.value?.copy(loadErrorMessage = "일기를 불러오는데 실패했습니다.")
                    }
                }
            }
        }
    }

    fun onEvent(event: DetailDiaryEvent) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }

        when (event) {
            is DetailDiaryEvent.RemoveDiary -> {
                viewModelScope.launch {
                    repository.deleteDiary(event.diaryId)
                }
            }
            else -> {

            }
        }
    }

}