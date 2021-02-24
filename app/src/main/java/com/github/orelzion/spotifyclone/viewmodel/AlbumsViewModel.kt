package com.github.orelzion.spotifyclone.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.github.orelzion.spotifyclone.model.AlbumsResponse
import com.github.orelzion.spotifyclone.model.repository.BrowseRepository
import com.github.orelzion.spotifyclone.view.AlbumListFragment
import java.lang.Error

class AlbumsViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    companion object {
        private const val ALBUMS_LIST_LIVE_DATA = "albums_list_live_data"
        private const val RESPONSE_ERROR = "response_error"
    }
    /**
     * Data
     */

    /**
     * holds the data in ViewModel
     */
    val albumsListLiveData =
        savedStateHandle.getLiveData<List<AlbumViewData>>(ALBUMS_LIST_LIVE_DATA)

    /**
     * Error
     */

    /**
     * Error Live Data Observer
     */
    private val responseErrorLiveData =
        savedStateHandle.getLiveData<Error>(RESPONSE_ERROR)


    /**
     * from ViewModel level call Model level (Repository)
     */
    fun loadAlbums() {
        BrowseRepository.fetchNewReleases { response, t ->
            if (response != null) {
                albumsListLiveData.postValue(responseToAlbumsData(response))
            }
            else {
                // TODO: t as Error? is right?
                responseErrorLiveData.postValue(t as Error?)

                Log.e(AlbumListFragment::javaClass.name, "Failed to load albums data", t)
            }
        }
    }

    private fun responseToAlbumsData(response: AlbumsResponse): List<AlbumViewData> {
        return response.items.map {
            AlbumViewData(
                id = it.id,
                imageUrl = it.images.first().url,
                title = it.name,
                artistName = it.artists.first().name,
                totalTracks = it.totalTracks
            )
        }
    }
}