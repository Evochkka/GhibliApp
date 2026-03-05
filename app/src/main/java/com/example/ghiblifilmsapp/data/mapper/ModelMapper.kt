package com.example.ghiblifilmsapp.data.mapper

import com.example.ghiblifilmsapp.data.dto.FilmDto
import com.example.ghiblifilmsapp.data.dto.PeopleDto
import com.example.ghiblifilmsapp.domain.model.Film
import com.example.ghiblifilmsapp.domain.model.FilmDetail
import com.example.ghiblifilmsapp.domain.model.People
import com.example.ghiblifilmsapp.domain.model.PeopleDetail
import com.example.ghiblifilmsapp.data.dto.LocationDto
import com.example.ghiblifilmsapp.domain.model.Location
import com.example.ghiblifilmsapp.domain.model.LocationDetail

fun FilmDto.toFilm(): Film = Film(
    id = id,
    title = title,
    description = description,
    imageUrl = image ?: movieBanner,
    releaseDate = releaseDate,
    director = director,
    rating = rtScore,
    runningTime = runningTime ?: "N/A"
)

fun FilmDto.toFilmDetail(): FilmDetail = FilmDetail(
    id = id,
    title = title,
    description = description,
    director = director,
    producer = producer,
    releaseDate = releaseDate,
    rtScore = rtScore,
    imageUrl = image,
    bannerUrl = movieBanner
)

fun PeopleDto.toPeople(): People = People(
    id = id,
    name = name,
    gender = gender
)

fun PeopleDto.toPeopleDetail(): PeopleDetail = PeopleDetail(
    id = id,
    name = name,
    gender = gender,
    age = age,
    eyeColor = eyeColor,
    hairColor = hairColor
)

fun LocationDto.toLocation(): Location = Location(
    id = id,
    name = name,
    terrain = terrain
)

fun LocationDto.toLocationDetail(): LocationDetail = LocationDetail(
    id = id,
    name = name,
    climate = climate,
    terrain = terrain,
    surfaceWater = surfaceWater
)