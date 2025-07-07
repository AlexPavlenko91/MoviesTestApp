package com.example.moviestest.presentation.screen.movies

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.moviestest.domain.model.Movie
import com.example.moviestest.domain.model.YearMonthKey
import com.example.moviestest.domain.repository.FavoritesRepository
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
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MoviesViewModel @Inject constructor(
    getPagedMoviesUseCase: GetPagedMoviesUseCase,
    favoritesRepository: FavoritesRepository,
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

    fun selectTab(tab: MoviesTab) {
        _selectedTab.value = tab
        savedStateHandle[TAB_KEY] = tab
    }

    private val favoritesFlow: StateFlow<List<Movie>> = favoritesRepository.getFavorites()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val favoriteIds: StateFlow<Set<Int>> = favoritesFlow
        .map { movies -> movies.map { it.id }.toSet() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptySet())

    val favoritesGrouped: StateFlow<Map<YearMonthKey, List<Movie>>> = favoritesFlow
        .map { groupByMonthUseCase(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyMap())


    private val pagerFlow = getPagedMoviesUseCase().cachedIn(viewModelScope)

    val pagedMovies: Flow<PagingData<Movie>> = pagerFlow
        .combine(favoriteIds) { paging, favs -> paging.map { movie -> movie.copy(isFavorite = favs.contains(movie.id)) } }


    val uiState: StateFlow<MoviesUiState> = combine(
        selectedTab, favoritesGrouped
    ) { tab, grouped ->
        MoviesUiState.Content(
            selectedTab = tab,
            favoritesGrouped = grouped
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = MoviesUiState.Loading
    )

    fun onToggleFavorite(movie: Movie) {
        viewModelScope.launch {
            val isFavorite = favoriteIds.value.contains(movie.id)
            toggleFavoriteUseCase(movie, isFavorite)
        }
    }
}