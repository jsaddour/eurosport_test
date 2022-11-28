package com.jsaddour.eurosporttestapp.features.Articles.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jsaddour.domain.usecases.ObserveArticlesUsecase
import com.jsaddour.domain.usecases.RefreshArticlesUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModel @Inject constructor(
    private val observeArticlesUsecase: ObserveArticlesUsecase,
    private val refreshArticlesUsecase: RefreshArticlesUsecase
) : ViewModel() {

    private val _viewState = MutableLiveData(ViewState.firstState(::refresh))
    val viewState: LiveData<ViewState> = _viewState

    init {

        viewModelScope.launch {
            observeArticlesUsecase.execute().collect { articles ->
                _viewState.value = viewState.value?.update(articles)
            }
        }
        refresh()
    }

   private fun refresh(){
        viewModelScope.launch {
            _viewState.value = viewState.value?.loading()
            val isSuccessful = refreshArticlesUsecase.execute()
            if (!isSuccessful) {
                _viewState.value = viewState.value?.error("an error occured")
            }
        }
    }
}
