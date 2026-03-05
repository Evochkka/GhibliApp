package com.example.ghiblifilmsapp.data.repository

import com.example.ghiblifilmsapp.domain.model.Film
import com.example.ghiblifilmsapp.domain.model.FilmDetail
import com.example.ghiblifilmsapp.domain.model.People
import com.example.ghiblifilmsapp.domain.model.PeopleDetail

interface FilmRepository {
    suspend fun getFilms(): List<Film>
    suspend fun getFilmDetail(id: String): FilmDetail
    suspend fun checkIfFilmExists(id: String): Boolean
}

interface PeopleRepository {
    suspend fun getPeoples(): List<People>
    suspend fun getPeopleDetail(id: String): PeopleDetail
    suspend fun checkIfPeopleExists(id: String): Boolean
}

interface LocationRepository {
    suspend fun getLocations(): List<com.example.ghiblifilmsapp.domain.model.Location>
    suspend fun getLocationDetail(id: String): com.example.ghiblifilmsapp.domain.model.LocationDetail
    suspend fun checkIfLocationExists(id: String): Boolean
}