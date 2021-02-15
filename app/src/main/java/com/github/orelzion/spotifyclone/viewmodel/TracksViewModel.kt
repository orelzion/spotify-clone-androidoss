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

    /**
     * Data
     */

    private var tracksListViewData = emptyList<TrackViewData>()

    // Tracks list ViewModel's Observer
    private val tracksListLiveData = MutableLiveData<List<TrackViewData>>()

    // sort of a getter to tracksListLiveData
    fun tracksListBindViewData(): LiveData<List<TrackViewData>> = tracksListLiveData

    // Selected Track ViewModel's Observer
    private val selectedTrackLiveData = MutableLiveData<TrackViewData>()

    fun selectedTrackBindViewData(): LiveData<TrackViewData> = selectedTrackLiveData


    /**
     * Error
     */

    private val responseError = MutableLiveData<Error>()

    fun bindErrorData() : LiveData<Error> = responseError



    fun onSelectedTrack(trackData: TrackViewData) {
        selectedTrackLiveData.postValue(trackData)
    }

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
                    // I'm pretty sure I get it wrong
                    // TODO: I still have to find out what is the right data to send back.
                responseError.postValue(responseError.value)

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