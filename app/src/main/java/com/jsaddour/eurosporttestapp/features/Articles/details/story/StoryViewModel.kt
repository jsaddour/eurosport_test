package com.jsaddour.eurosporttestapp.features.Articles.details.story

import androidx.lifecycle.*
import com.jsaddour.domain.usecases.GetStoryUsecase
import com.jsaddour.eurosporttestapp.features.Articles.details.story.StoryActivity.Companion.STORY_ID_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoryViewModel @Inject constructor(
    private val getStoryUsecase: GetStoryUsecase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _viewState = MutableLiveData(ViewState(story = null))
    val viewState: LiveData<ViewState> = _viewState

    val storyId: Int = savedStateHandle.get(STORY_ID_KEY) ?: throw IllegalArgumentException(
        "STORY_ID_KEY should not be empty"
    )

    init {
        viewModelScope.launch {
            getStoryUsecase.execute(storyId)?.let { story ->
                _viewState.value = requireNotNull(viewState.value).copy(
                    story = story.toStoryItem()
                )
            }
        }
    }
}


