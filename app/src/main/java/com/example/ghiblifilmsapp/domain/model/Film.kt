package com.example.ghiblifilmsapp.domain.model

import com.example.ghiblifilmsapp.data.dto.FilmDto

data class Film(
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String?,
    val releaseDate: String,
    val director: String,
    val rating: String,
    val runningTime: String
)

data class FilmDetail(
    val id: String?,
    val title: String?,
    val description: String?,
    val director: String?,
    val producer: String?,
    val releaseDate: String?,
    val rtScore: String?,
    val imageUrl: String?,
    val bannerUrl: String?
)

data class FilmResponse(
    val data: List<FilmDto>
)
