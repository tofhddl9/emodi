package com.lgtm.emoji_diary.view.home.timeline

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lgtm.emoji_diary.data.Diary
import com.lgtm.emoji_diary.data.source.DiaryRepository
import com.lgtm.emoji_diary.data.source.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class TimelineViewModel @Inject constructor(
    private val diaryRepository: DiaryRepository,
) : ViewModel() {

    private val _uiState = MutableLiveData(TimelineUiState())
    val uiState: LiveData<TimelineUiState>
        get() = _uiState

    fun getDiaries() {
        viewModelScope.launch {
            val result = diaryRepository.getDiaries()
            when (result) {
                is Result.Success -> {
                    _uiState.value = _uiState.value?.copy(
                        // diaryList = result.data
                        // diaryList = listOf(Diary(id=1, title="test"))
                    )
                }
                is Result.Error -> {
                    _uiState.value = _uiState.value?.copy(
                        loadErrorMessage = "타임라인을 불러오는데 실패했습니다."
                    )
                }
            }
        }
    }

}