package edu.skillbox.architecture.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import edu.skillbox.architecture.entity.UsefulActivity

@JsonClass(generateAdapter = true)
class UsefulActivityDto(
    @Json(name = "activity") override val activity: String,
    @Json(name = "key") override val key: String?,
    @Json(name = "link") override val link: String?,
    @Json(name = "participants") override val participants: Int?,
    @Json(name = "price") override val price: Double?,
    @Json(name = "type") override val type: String?
) : UsefulActivity