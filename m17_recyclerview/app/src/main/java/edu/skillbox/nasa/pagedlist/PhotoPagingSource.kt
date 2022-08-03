package edu.skillbox.nasa.pagedlist

import androidx.paging.PagingSource
import androidx.paging.PagingState
import edu.skillbox.nasa.models.Photo

class PhotoPagingSource : PagingSource<Int, Photo> (){
    private val repository = PhotoPagedListRepository()
    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? = FIRST_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        val page = params.key ?: FIRST_PAGE
        return kotlin.runCatching {
            repository.getTopList(page)
        }.fold(
            onSuccess = {
                LoadResult.Page(
                    data = it,
                    prevKey = null,
                    nextKey = if (it.isEmpty()) null else page + 1
                )
            },
            onFailure = { LoadResult.Error(it) }
        )
    }

    private companion object{
        private val FIRST_PAGE = 0
    }
}