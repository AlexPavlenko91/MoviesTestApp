package com.example.moviestest.domain.usecase

import com.example.moviestest.domain.model.Movie
import com.example.moviestest.domain.model.YearMonthKey
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject


class GroupMoviesByMonthUseCase @Inject constructor() {

    private val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    operator fun invoke(movies: List<Movie>): Map<YearMonthKey, List<Movie>> {
        return movies
            .filter { it.releaseDate?.isNotBlank() == true }
            .mapNotNull { movie ->
                val date = try {
                    movie.releaseDate?.let { formatter.parse(it) }
                } catch (e: ParseException) {
                    null
                }
                date?.let {
                    val calendar = Calendar.getInstance().apply { time = it }
                    val key =
                        YearMonthKey(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1)
                    key to movie
                }
            }
            .groupBy({ it.first }, { it.second })
            .toSortedMap(compareByDescending<YearMonthKey> { it.year }
                .thenByDescending { it.month })
    }
}