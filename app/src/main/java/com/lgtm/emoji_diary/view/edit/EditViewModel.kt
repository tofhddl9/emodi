package com.lgtm.emoji_diary.view.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lgtm.emoji_diary.data.source.DiaryRepository
import com.lgtm.emoji_diary.data.Result
import com.lgtm.emoji_diary.utils.CalendarUtil
import com.lgtm.emoji_diary.view.edit.validate.ValidateContent
import com.lgtm.emoji_diary.view.edit.validate.ValidateTitle
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

@HiltViewModel
class EditViewModel @Inject constructor(
    private val repository: DiaryRepository,
    private val validateTitle: ValidateTitle,
    private val validateContent: ValidateContent,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableLiveData(EditUiState())
    val uiState: LiveData<EditUiState>
        get() = _uiState

    private val _emojiPickerClicked = MutableSharedFlow<Boolean>()
    val emojiPickerClicked = _emojiPickerClicked.asSharedFlow()

    private val _datePickerClicked = MutableSharedFlow<SimpleDate>()
    val datePickerClicked = _datePickerClicked.asSharedFlow()

    private val _timePickerClicked = MutableSharedFlow<SimpleDate>()
    val timePickerClicked = _timePickerClicked.asSharedFlow()

    private val _saveAndQuit = MutableSharedFlow<Boolean>()
    val saveAndQuit = _saveAndQuit.asSharedFlow()

    fun loadDiary(diaryId: Long) {
        if (diaryId == -1L) {
            val currentTimeInMillis = CalendarUtil.getCurrentTimeInMills()
            val currentDate = timeInMillisToSimpleDate(currentTimeInMillis)
            _uiState.value = _uiState.value?.copy(
                date = currentDate.asDateFormat(),
                time = currentDate.asTimeFormat(),
            )
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
                        )
                    }
                    is Result.Error -> {
                        // show some Message?
                    }
                }
            }
        }
    }

    fun onEvent(event: EditDiaryEvent) {
        when (event) {
            is EditDiaryEvent.DateChanged -> {
                _uiState.value = _uiState.value?.copy(date = event.simpleDate.asDateFormat())
            }
            is EditDiaryEvent.TimeChanged -> {
                _uiState.value = _uiState.value?.copy(time = event.simpleDate.asTimeFormat())
            }
            is EditDiaryEvent.TitleChanged -> {
                _uiState.value = _uiState.value?.copy(title = event.title)
            }
            is EditDiaryEvent.ContentChanged -> {
                _uiState.value = _uiState.value?.copy(content = event.content)
            }
            is EditDiaryEvent.EmojiChanged -> {
                _uiState.value = _uiState.value?.copy(emojiId = event.emojiId)
            }
            is EditDiaryEvent.EmojiPickerClicked -> {
                viewModelScope.launch {
                    _emojiPickerClicked.emit(true)
                }
            }
            is EditDiaryEvent.DatePickerClicked -> {
                viewModelScope.launch {
                    _datePickerClicked.emit(event.date)
                }
            }
            is EditDiaryEvent.TimePickerClicked -> {
                viewModelScope.launch {
                    _timePickerClicked.emit(event.date)
                }
            }
            is EditDiaryEvent.Save -> {
                // TODO : validate diary form

                viewModelScope.launch(Dispatchers.IO) {
                    val diaryId = savedStateHandle.get<Long>("diaryId") ?: 0L
                    _uiState.value?.mapToDiary(diaryId)?.let {
                        repository.insertOrUpdateDiary(it)
                        _saveAndQuit.emit(true)

                    }
                }
            }
        }
    }
}