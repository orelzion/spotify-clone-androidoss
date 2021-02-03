package com.github.orelzion.spotifyclone.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.github.orelzion.spotifyclone.R
import com.github.orelzion.spotifyclone.model.Tracks
import com.github.orelzion.spotifyclone.model.TracksResponse
import com.github.orelzion.spotifyclone.model.repository.BrowseRepository
import com.github.orelzion.spotifyclone.viewmodel.TrackViewData


/**
 * A fragment representing a list of Tracks.
 */
class TracksListFragment : Fragment(R.layout.fragment_items_list) {
    private val tracksListView: RecyclerView by lazy { requireView().findViewById(R.id.itemsList) }
    private val tracksAdapter: TracksAdapter by lazy { TracksAdapter() }

    /**
     *
     */
    companion object {
        /**
         * factory method
         * accessed via TracksListFragment, as it is being declared in companion object
         */
        fun newInstance(albumId: String): TracksListFragment {
            return TracksListFragment().apply {
                arguments = Bundle().apply {
                    putString("albumId", albumId)
                    // TODO: Ask Orel - Shall I declare albumId as a Pair? in/outside companion object?
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tracksListView.adapter = tracksAdapter

        val albumId = requireArguments().getString("albumId")

        if (savedInstanceState == null) {
            albumId?.let {
                loadTracks(it)
            }
        }
        // TODO: add else - restore savedInstanceState rather than (re-)loadSongs

        requireView().findViewById<ProgressBar>(R.id.progressBar).isVisible = false
    }

    private fun loadTracks(albumId: String) {
        BrowseRepository.fetchAlbumDetails(albumId) { response: TracksResponse?, t: Throwable? ->
            if (response != null) {
                tracksAdapter.submitList(responseToTracksData(response))
            } else {
                Toast.makeText(requireContext(), R.string.loading_error, Toast.LENGTH_LONG).show()
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