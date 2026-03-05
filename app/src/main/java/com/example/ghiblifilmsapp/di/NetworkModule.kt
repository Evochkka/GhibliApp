package com.example.ghiblifilmsapp.di

import com.example.ghiblifilmsapp.data.remote.GhibliApi
import com.example.ghiblifilmsapp.data.repository.FilmRepository
import com.example.ghiblifilmsapp.data.repository.FilmRepositoryImpl
import com.example.ghiblifilmsapp.data.repository.LocationRepository
import com.example.ghiblifilmsapp.data.repository.LocationRepositoryImpl
import com.example.ghiblifilmsapp.data.repository.PeopleRepository
import com.example.ghiblifilmsapp.data.repository.PeopleRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://ghibli-api.vercel.app/api/"

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideGhibliApi(retrofit: Retrofit): GhibliApi {
        return retrofit.create(GhibliApi::class.java)
    }

    @Provides
    @Singleton
    fun provideFilmRepository(api: GhibliApi): FilmRepository {
        return FilmRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun providePeopleRepository(api: GhibliApi): PeopleRepository {
        return PeopleRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideLocationRepository(api: GhibliApi): LocationRepository {
        return LocationRepositoryImpl(api)
    }
}