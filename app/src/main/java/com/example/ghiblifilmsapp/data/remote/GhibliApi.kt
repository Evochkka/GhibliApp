package com.example.ghiblifilmsapp.data.remote

import com.example.ghiblifilmsapp.data.dto.FilmDetailResponse
import com.example.ghiblifilmsapp.data.dto.LocationDetailResponse
import com.example.ghiblifilmsapp.data.dto.PeopleDetailResponse
import com.example.ghiblifilmsapp.domain.model.FilmResponse
import com.example.ghiblifilmsapp.domain.model.PeopleResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface GhibliApi {
    @GET("films")
    suspend fun getFilms(): FilmResponse

    @GET("films/{id}")
    suspend fun getFilm(@Path("id") id: String): retrofit2.Response<FilmDetailResponse>

    @GET("people")
    suspend fun getPeoples(): PeopleResponse

    @GET("people/{id}")
    suspend fun getPeople(@Path("id") id: String): retrofit2.Response<PeopleDetailResponse>

    @GET("locations")
    suspend fun getLocations(): com.example.ghiblifilmsapp.domain.model.LocationResponse

    @GET("locations/{id}")
    suspend fun getLocation(@Path("id") id: String): retrofit2.Response<LocationDetailResponse>
}