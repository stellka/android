package edu.skillbox.nasa.pagedlist

import edu.skillbox.nasa.api.retrofit
import edu.skillbox.nasa.models.Photo
import kotlinx.coroutines.delay

class PhotoPagedListRepository {
    suspend fun getTopList(page:Int): List<Photo> {
        return retrofit.topList(page).photos
    }
}