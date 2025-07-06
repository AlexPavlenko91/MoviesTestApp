package com.example.moviestest.di

import com.example.moviestest.data.remote.api.MovieApi
import com.example.moviestest.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Provides
    fun provideAuthInterceptor(): Interceptor = Interceptor { chain ->
        val request = chain.request().newBuilder()
            .addHeader(HEADER_AUTHORIZATION, BEARER_PREFIX + BuildConfig.TMDB_ACCESS_TOKEN)
            .addHeader(HEADER_ACCEPT, ACCEPT_JSON)
            .build()
        chain.proceed(request)
    }

    @Provides
    fun provideOkHttpClient(authInterceptor: Interceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()

    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

    @Provides
    fun provideMovieApi(retrofit: Retrofit): MovieApi =
        retrofit.create(MovieApi::class.java)

    private const val HEADER_AUTHORIZATION = "Authorization"
    private const val HEADER_ACCEPT = "accept"
    private const val ACCEPT_JSON = "application/json"
    private const val BEARER_PREFIX = "Bearer "
}