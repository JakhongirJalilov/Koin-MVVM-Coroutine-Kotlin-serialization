package uz.fozilbekimomov.koinmvvmcoroutine.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uz.fozilbekimomov.koinmvvmcoroutine.core.models.HomeData
import uz.fozilbekimomov.koinmvvmcoroutine.core.network.service.HomeServices
import uz.fozilbekimomov.koinmvvmcoroutine.core.utils.DataState
import uz.fozilbekimomov.koinmvvmcoroutine.core.utils.Event


/**
 * Created by <a href="mailto: fozilbekimomov@gmail.com" >Fozilbek Imomov</a>
 *
 * @author fozilbekimomov
 * @version 1.0
 * @date 9/28/20
 * @project Koin+MVVM+Coroutine
 */


class HomeVM(private var homeServices: HomeServices) : ViewModel(), HomeContract.VM {

    private val _moviesLiveData = MutableLiveData<DataState>()
    override val moviesLiveData: LiveData<DataState>
        get() = _moviesLiveData

    override fun loadPopularMovie() {
        viewModelScope.launch {
            runCatching {
                emitUiState(true)
                homeServices.getPopularMovies(1)
            }.onSuccess {
                val homeData=HomeData(0,"Most Popular",it.movies)
                emitUiState(movies = Event(homeData))
            }.onFailure {
                emitUiState(error = Event(it.toString()))
            }
        }
    }

    override fun loadNowPlayingMovies() {
        viewModelScope.launch {
            runCatching {
                emitUiState(true)
                homeServices.getNowPlayingMovies(1)
            }.onSuccess {
                val homeData=HomeData(1,"Now Playing",it.movies)
                emitUiState(movies = Event(homeData))
            }.onFailure {
                emitUiState(error = Event(it.toString()))
            }
        }
    }

    override fun loadTopRated() {
        viewModelScope.launch {
            runCatching {
                emitUiState(true)
                homeServices.getTopRatedMovies(1)
            }.onSuccess {
                val homeData=HomeData(2,"Top Rated",it.movies)
                emitUiState(movies = Event(homeData))
            }.onFailure {
                emitUiState(error = Event(it.toString()))
            }
        }
    }

    override fun loadUpcoming() {
        viewModelScope.launch {
            runCatching {
                emitUiState(true)
                homeServices.getUpcomingMovies(1)
            }.onSuccess {
                val homeData=HomeData(3,"Upcoming Movies",it.movies)
                emitUiState(movies = Event(homeData))
            }.onFailure {
                emitUiState(error = Event(it.toString()))
            }
        }
    }

    private fun emitUiState(
        showProgress: Boolean = false,
        movies: Event<HomeData>? = null,
        error: Event<String>? = null
    ) {
        val dataState = DataState(showProgress, movies, error)
        _moviesLiveData.value = dataState
    }


}