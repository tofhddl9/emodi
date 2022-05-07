package com.lgtm.emodi.view.home.timeline

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lgtm.emodi.data.source.DiaryRepository
import com.lgtm.emodi.data.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

const val MAX_COUNT_OF_TIMELINE_DIARY = 100

@HiltViewModel
class TimelineViewModel @Inject constructor(
    private val diaryRepository: DiaryRepository,
) : ViewModel() {

    private val _uiState = MutableLiveData(TimelineUiState())
    val uiState: LiveData<TimelineUiState>
        get() = _uiState

    fun getDiaries() {
        viewModelScope.launch {
            diaryRepository.getDiaries(MAX_COUNT_OF_TIMELINE_DIARY).collect { result ->
                when (result) {
                    is Result.Success -> {
                        _uiState.value = _uiState.value?.copy(
                            diaryList = result.data
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
}