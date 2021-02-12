package com.github.orelzion.spotifyclone.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
//import android.widget.Toast
import androidx.lifecycle.ViewModel
//import com.github.orelzion.spotifyclone.R
import com.github.orelzion.spotifyclone.model.TracksResponse
import com.github.orelzion.spotifyclone.model.repository.BrowseRepository
import com.github.orelzion.spotifyclone.view.AlbumListFragment

class TracksViewModel : ViewModel() {

    private var tracksListViewData = emptyList<TrackViewData>()

    // ViewModel's Observer
    private val tracksListLiveData = MutableLiveData<List<TrackViewData>>()

    // sort of getter to tracksListLiveData
    fun bindViewData(): LiveData<List<TrackViewData>> = tracksListLiveData

    fun loadTracks(albumId : String) {
        // As a request for loading tracks is received from View layer, a compatible request is
        // sent to the Model layer (represented by Repository)
        BrowseRepository.fetchAlbumDetails(albumId) { response: TracksResponse?, t: Throwable? ->
            if (response != null) {
                // Upon response arrival from Repository, it is being broadcast
                    // through Observer (represented by MutableLiveData)
                tracksListViewData = responseToTracksData(response)
                tracksListLiveData.postValue(tracksListViewData)
            } else {
                // For no response, a proper message is broadcast to Observer
                    // TODO: Find out what is the right data to send back.
                    tracksListLiveData.postValue(null)

                // TODO: Ask Orel if AlbumListFragment shouldn't be replaced with TracksListFragment
                Log.e(AlbumListFragment::javaClass.name, "Failed to load tracks data", t)
            }
        }
    }

    private fun responseToTracksData(response: TracksResponse): List<TrackViewData> {
        return response.tracks.items.map {
            TrackViewData(
                id = it.id,
                name = it.name,
                duration = it.duration,
                trackNumber = it.trackNumber,
                externalUrl = it.externalUrl
            )
        }
    }
}