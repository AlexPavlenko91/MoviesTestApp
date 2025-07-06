package com.example.moviestest.presentation.screen.movies

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.moviestest.domain.model.Movie
import com.example.moviestest.domain.usecase.GetFavoritesUseCase
import com.example.moviestest.domain.usecase.GetPagedMoviesUseCase
import com.example.moviestest.domain.usecase.GroupMoviesByMonthUseCase
import com.example.moviestest.domain.usecase.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MoviesViewModel @Inject constructor(
    getPagedMoviesUseCase: GetPagedMoviesUseCase,
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val groupByMonthUseCase: GroupMoviesByMonthUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        private const val TAB_KEY = "selected_tab"
    }

    private val _selectedTab = MutableStateFlow(
        savedStateHandle.get<MoviesTab>(TAB_KEY) ?: MoviesTab.All
    )
    private val selectedTab: StateFlow<MoviesTab> = _selectedTab.asStateFlow()

    val pagedMovies: Flow<PagingData<Movie>> = getPagedMoviesUseCase()


    private val favoritesFlow: Flow<List<Movie>> = flow {
        getFavoritesUseCase().collect { favorites ->
            emit(favorites)
        }
    }

    val uiState: StateFlow<MoviesUiState> = combine(
        selectedTab, favoritesFlow
    ) { tab, favs ->
        val grouped = groupByMonthUseCase(favs)
        MoviesUiState.Content(
            selectedTab = tab,
            favoritesGrouped = grouped
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = MoviesUiState.Loading
    )

    fun selectTab(tab: MoviesTab) {
        _selectedTab.value = tab
        savedStateHandle[TAB_KEY] = tab
    }

    fun onToggleFavorite(movie: Movie) {
        viewModelScope.launch {
            toggleFavoriteUseCase(movie.id, !movie.isFavorite)
        }
    }
}