package com.example.ghiblifilmsapp.data.repository

import com.example.ghiblifilmsapp.data.mapper.toFilm
import com.example.ghiblifilmsapp.data.mapper.toFilmDetail
import com.example.ghiblifilmsapp.data.mapper.toLocation
import com.example.ghiblifilmsapp.data.mapper.toLocationDetail
import com.example.ghiblifilmsapp.data.mapper.toPeople
import com.example.ghiblifilmsapp.data.mapper.toPeopleDetail
import com.example.ghiblifilmsapp.data.remote.GhibliApi
import com.example.ghiblifilmsapp.domain.model.Film
import com.example.ghiblifilmsapp.domain.model.FilmDetail
import com.example.ghiblifilmsapp.domain.model.Location
import com.example.ghiblifilmsapp.domain.model.LocationDetail
import com.example.ghiblifilmsapp.domain.model.People
import com.example.ghiblifilmsapp.domain.model.PeopleDetail
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FilmRepositoryImpl @Inject constructor(
    private val api: GhibliApi
) : FilmRepository {

    override suspend fun getFilms(): List<Film> =
        api.getFilms().data.map { it.toFilm() }

    override suspend fun getFilmDetail(id: String): FilmDetail {
        val response = api.getFilm(id)
        if (response.isSuccessful) {
            return response.body()?.data?.toFilmDetail() ?: throw Exception("Empty body")
        } else throw Exception("Error: ${response.code()}")
    }

    override suspend fun checkIfFilmExists(id: String): Boolean {
        return try { api.getFilm(id).isSuccessful } catch (e: Exception) { false }
    }
}

@Singleton
class PeopleRepositoryImpl @Inject constructor(
    private val api: GhibliApi
) : PeopleRepository {

    override suspend fun getPeoples(): List<People> =
        api.getPeoples().data.map { it.toPeople() }

    override suspend fun getPeopleDetail(id: String): PeopleDetail {
        val response = api.getPeople(id)
        if (response.isSuccessful) {
            return response.body()?.data?.toPeopleDetail() ?: throw Exception("Empty body")
        } else throw Exception("Error: ${response.code()}")
    }

    override suspend fun checkIfPeopleExists(id: String): Boolean {
        return try { api.getPeople(id).isSuccessful } catch (e: Exception) { false }
    }
}

@Singleton
class LocationRepositoryImpl @Inject constructor(
    private val api: GhibliApi
) : LocationRepository {

    override suspend fun getLocations(): List<Location> =
        api.getLocations().data.map { it.toLocation() }

    override suspend fun getLocationDetail(id: String): LocationDetail {
        val response = api.getLocation(id)
        if (response.isSuccessful) {
            return response.body()?.data?.toLocationDetail() ?: throw Exception("Empty body")
        } else throw Exception("Error: ${response.code()}")
    }

    override suspend fun checkIfLocationExists(id: String): Boolean {
        return try { api.getLocation(id).isSuccessful } catch (e: Exception) { false }
    }
}

