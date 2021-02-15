package com.github.orelzion.spotifyclone.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.orelzion.spotifyclone.model.AlbumsResponse
import com.github.orelzion.spotifyclone.model.repository.BrowseRepository
import com.github.orelzion.spotifyclone.view.AlbumListFragment

class AlbumsViewModel : ViewModel() {

    // holds the data in ViewModel
    private var albumsListViewData = emptyList<AlbumViewData>()

    // Observer
    private val albumsListLiveData = MutableLiveData<List<AlbumViewData>>()

    // sort of getter to albumsListLiveDAta
    fun bindViewData() : LiveData<List<AlbumViewData>> = albumsListLiveData


    fun loadAlbums() {
        BrowseRepository.fetchNewReleases { response, t ->
            if (response != null) {
                albumsListViewData = responseToAlbumsData(response)
                albumsListLiveData.postValue(albumsListViewData)
            }
            else {
                // TODO: figure out what is the right data to broadcast
                albumsListLiveData.postValue(null)

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