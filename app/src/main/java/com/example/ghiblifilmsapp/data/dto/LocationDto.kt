package com.example.ghiblifilmsapp.data.dto

import com.google.gson.annotations.SerializedName

data class LocationDto(
    val id: String,
    val name: String,
    val climate: String,
    val terrain: String,
    @SerializedName("surface_water") val surfaceWater: String
)

data class LocationDetailResponse(
    val data: LocationDto

)