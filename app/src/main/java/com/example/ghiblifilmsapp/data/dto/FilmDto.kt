package com.example.ghiblifilmsapp.data.dto

import com.google.gson.annotations.SerializedName

data class FilmDto(
    val id: String,
    val title: String,
    val description: String,
    val director: String,
    val producer: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("rt_score") val rtScore: String,
    @SerializedName("image") val image: String?,
    @SerializedName("movie_banner") val movieBanner: String?,
    @SerializedName("running_time") val runningTime: String?
)

data class FilmDetailResponse(
    val data: FilmDto
)