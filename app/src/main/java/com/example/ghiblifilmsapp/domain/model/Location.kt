package com.example.ghiblifilmsapp.domain.model
import com.example.ghiblifilmsapp.data.dto.LocationDto

data class Location(
    val id: String,
    val name: String,
    val terrain: String
)

data class LocationDetail(
    val id: String?,
    val name: String?,
    val climate: String?,
    val terrain: String?,
    val surfaceWater: String?
)

data class LocationResponse(
    val data: List<LocationDto>
)