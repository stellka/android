package edu.skillbox.nasa.photolist

import edu.skillbox.nasa.api.retrofit
import edu.skillbox.nasa.models.Photo

class PhotoListRepository {
    suspend fun getPhotos(): List<Photo> {
        return retrofit.getPhotosInfoList().photos
    }
}