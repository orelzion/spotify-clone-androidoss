package com.github.orelzion.spotifyclone.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.github.orelzion.spotifyclone.model.TracksResponse
import com.github.orelzion.spotifyclone.model.repository.BrowseRepository
import com.github.orelzion.spotifyclone.view.AlbumListFragment

class TracksViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    companion object {
        private const val TRACKS_LIST_LIVE_DATA = "tracks_list_live_data"
        private const val SELECTED_TRACK_LIVE_DATA = "selected_track_live_data"
        private const val RESPONSE_ERROR_DATA = "response_error_data"
    }

    /**
     * Data
     */

    /**
     * Tracks list ViewModel's Observer, refers to Saved State Handle.
     */
    val tracksListLiveData =
        savedStateHandle.getLiveData<List<TrackViewData>>(TRACKS_LIST_LIVE_DATA, emptyList())

    /**
     * Selected Track ViewModel's Observer, refers to Saved State Handle.
     */
    val selectedTrackLiveData =
        savedStateHandle.getLiveData<TrackViewData>(SELECTED_TRACK_LIVE_DATA)

    /**
     * Error
     */
    private val responseErrorLiveData =
        savedStateHandle.getLiveData<Error>(RESPONSE_ERROR_DATA)


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
                tracksListLiveData.postValue(responseToTracksData(response))
            } else {
                //TODO: is t as Error? right?
                responseErrorLiveData.postValue(t as Error?)

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