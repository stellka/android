package edu.skillbox.randomuser

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class User(
    @Json(name = "results") val results: List<Result>?
)

@JsonClass(generateAdapter = true)
data class Result(
    @Json(name = "email") val email: String,
    @Json(name = "gender") val gender: String,
    @Json(name = "name") val name: Name,
    @Json(name = "phone") val phone: String
)

@JsonClass(generateAdapter = true)
data class Dob(
    @Json(name = "age") val age: Int,
    @Json(name = "date") val date: String
)
@JsonClass(generateAdapter = true)
data class Name(
    @Json(name = "first") val first: String,
    @Json(name = "last") val last: String,
    @Json(name = "title") val title: String
)

@JsonClass(generateAdapter = true)
data class Picture(
    @Json(name = "large") val large: String?,
    @Json(name = "medium") val medium: String?,
    @Json(name = "thumbnail") val thumbnail: String?
)


@JsonClass(generateAdapter = true)
data class Registered(
    @Json(name = "age") val age: Int,
    @Json(name = "date") val date: String
)