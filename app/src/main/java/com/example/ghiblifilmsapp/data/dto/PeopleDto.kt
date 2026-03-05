package com.example.ghiblifilmsapp.data.dto

import com.google.gson.annotations.SerializedName


data class PeopleDto(
    val id: String,
    val name: String,
    val gender: String,
    val age: String,
    @SerializedName("eye_color") val eyeColor: String,
    @SerializedName("hair_color") val hairColor: String
)

data class PeopleDetailResponse(
    val data: PeopleDto
)