package com.github.orelzion.spotifyclone.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.orelzion.spotifyclone.model.AlbumsResponse
import com.github.orelzion.spotifyclone.model.repository.BrowseRepository
import com.github.orelzion.spotifyclone.view.AlbumListFragment

class AlbumsViewModel : ViewModel() {

    /**
     * Data
     */

    // holds the data in ViewModel
    private var albumsListViewData = emptyList<AlbumViewData>()

    // albums list Live Data Observer
    private val albumsListLiveData = MutableLiveData<List<AlbumViewData>>()

    // sort of getter to albumsListLiveDAta
    fun bindViewData() : LiveData<List<AlbumViewData>> = albumsListLiveData


    /**
     * Error
     */

    // Error Live Data Observer
    private val responseError = MutableLiveData<Error>()

    fun bindErrorData() : LiveData<Error> = responseError


    /**
     * from ViewModel level call Model level (Repository)
     */
    fun loadAlbums() {
        BrowseRepository.fetchNewReleases { response, t ->
            if (response != null) {
                albumsListViewData = responseToAlbumsData(response)
                albumsListLiveData.postValue(albumsListViewData)
            }
            else {
                // TODO: I'm pretty sure it's not the right way to pass an error;
                    //  I still have figure out what is the right data to broadcast
                responseError.postValue(responseError.value)

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