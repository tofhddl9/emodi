package com.lgtm.emodi.view.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lgtm.emodi.data.source.DiaryRepository
import com.lgtm.emodi.data.Result
import com.lgtm.emodi.data.SimpleDate
import com.lgtm.emodi.data.asDateFormat
import com.lgtm.emodi.data.asTimeFormat
import com.lgtm.emodi.data.timeInMillisToSimpleDate
import com.lgtm.emodi.utils.CalendarUtil
import com.lgtm.emodi.view.edit.validate.ValidateEmoji
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class EditViewModel @Inject constructor(
    private val repository: DiaryRepository,
//    private val validateTitle: ValidateTitle,
//    private val validateContent: ValidateContent,
    private val validateEmoji: ValidateEmoji,
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

    private val _imagePickerClicked = MutableSharedFlow<Boolean>()
    val imagePickerClicked = _imagePickerClicked.asSharedFlow()

    private val _saveAndQuit = MutableSharedFlow<Boolean>()
    val saveAndQuit = _saveAndQuit.asSharedFlow()

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun loadDiary(diaryId: Long, date: SimpleDate?) {
        if (diaryId == -1L) {
            val currentDate = date ?: timeInMillisToSimpleDate(CalendarUtil.getCurrentTimeInMills())
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
                            datePickerEnabled = diaryId == -1L,
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
                    // TODO : may remove after migrate livedata -> sharedFlow
                    _uiState.value = _uiState.value?.copy(emojiErrorMessage = null)
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
            is EditDiaryEvent.ImagePickerClicked -> {
                viewModelScope.launch {
                    _imagePickerClicked.emit(true)
                }
            }
            is EditDiaryEvent.Save -> {
                // TODO : validate diary form
                saveDiary()
            }
        }
    }

    private fun saveDiary() {
        val emojiResult = validateEmoji.invoke(_uiState.value!!.emojiId) // change to stateflow

        val hasError = listOf(
            emojiResult,
        ).any { !it.successful }

        if (hasError) {
            _uiState.value = _uiState.value!!.copy(
                emojiErrorMessage = emojiResult.errorMessage
            )
            return
        }
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }

        viewModelScope.launch(Dispatchers.IO) {
            val diaryId = savedStateHandle.get<Long>("diaryId") ?: 0L
            _uiState.value?.mapToDiary(diaryId)?.let {
                repository.insertOrUpdateDiary(it)
                _saveAndQuit.emit(true)
            }
        }
    }

    sealed class ValidationEvent {
        object Success : ValidationEvent()
    }
}