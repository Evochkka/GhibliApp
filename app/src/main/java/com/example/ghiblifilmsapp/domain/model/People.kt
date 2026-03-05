package com.example.ghiblifilmsapp.domain.model

import com.example.ghiblifilmsapp.data.dto.PeopleDto

data class People(
    val id: String,
    val name: String,
    val gender: String
)

data class PeopleDetail(
    val id: String,
    val name: String,
    val gender: String,
    val age: String,
    val eyeColor: String?,
    val hairColor: String?
)

data class PeopleResponse(
    val data: List<PeopleDto>
)