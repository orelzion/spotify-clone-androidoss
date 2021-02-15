package com.github.orelzion.spotifyclone.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
//import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
//import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.github.orelzion.spotifyclone.R
import com.github.orelzion.spotifyclone.viewmodel.TrackViewData
//import com.github.orelzion.spotifyclone.model.Tracks
//import com.github.orelzion.spotifyclone.model.TracksResponse
//import com.github.orelzion.spotifyclone.model.repository.BrowseRepository
//import com.github.orelzion.spotifyclone.viewmodel.TrackViewData
import com.github.orelzion.spotifyclone.viewmodel.TracksViewModel


/**
 * A fragment representing a list of Tracks.
 * Fragment is at the View level
 */
class TracksListFragment : Fragment(R.layout.fragment_items_list) {
    /**
     * View level
     */

    /**
     * Reference to RecyclerView that holds tracks' list
     */
    private val tracksListView: RecyclerView by lazy { requireView().findViewById(R.id.itemsList) }

    /**
     * Reference to an adapter for the RecyclerView
     */
    private val tracksAdapter: TracksAdapter by lazy { TracksAdapter() }


    /**
     * ViewModel level
     */

    /**
     * Reference to corresponding ViewModel
     * We use activityViewModels rather than viewModels because we don't want attach that ViewModel
     * to the current Fragment.
     */
    private val tracksViewModel by activityViewModels<TracksViewModel>()

    /**
     * companion object is an object created once for a class; it is not being duplicated for each instance
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
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tracksListView.adapter = tracksAdapter.apply {
            clickListener = this@TracksListFragment::onItemClicked
        }

        /**
         * From Bundle (created in AlbumsListFragment, when an album was clicked),
         * get album's id ->
         */
        val albumId = requireArguments().getString("albumId")
        /**
         * -> and load it's tracks (loadTracks() would call ViewModel's loadTracks())
         */
        if (savedInstanceState == null) {
            albumId?.let {
                loadTracks(it)
            }
        }
        // TODO: add else - restore savedInstanceState rather than (re-)loadSongs

        // hide progressbar
        requireView().findViewById<ProgressBar>(R.id.progressBar).isVisible = false
    }

    /**
     * onItemClicked - show corresponding tracks' list in main fragment
     */
    private fun onItemClicked(trackData: TrackViewData) {
        tracksViewModel.onSelectedTrack(trackData)
    }

    /**
     * loadTracks is being called in onViewCreated - meaning, when an album is clicked;
     * (in albumsListFragment) showFragment is called with TracksListFragment.newInstance(albumId)
     */
    private fun loadTracks(albumId: String) {
        /**
         * call ViewModel's loadTracks() - let it know that an album was clicked and pass albumId ->
         */
        requireArguments().getString("albumId")?.let { tracksViewModel.loadTracks(it) }
        /**
         * -> and observe it's reply -
         */
        tracksViewModel.tracksListBindViewData().observe(viewLifecycleOwner, {
            tracksAdapter.submitList(it)
        })

        // TODO: Error handling.
        // we may call
//        tracksViewModel.bindErrorData().observe(viewLifecycleOwner) {
//
//        }
    }
}