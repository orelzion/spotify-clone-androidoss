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
 */
class TracksListFragment : Fragment(R.layout.fragment_items_list) {
    private val tracksListView: RecyclerView by lazy { requireView().findViewById(R.id.itemsList) }
    private val tracksAdapter: TracksAdapter by lazy { TracksAdapter() }

    private val tracksViewModel by activityViewModels<TracksViewModel>()

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
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tracksListView.adapter = tracksAdapter.apply {
            clickListener = this@TracksListFragment::onItemClicked
        }

        val albumId = requireArguments().getString("albumId")

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
    // TODO: replace TracksListFragment.newInstance(albumId)
    //  with TrackFragment.newInstance(trackId (?) )
    private fun onItemClicked(trackData: TrackViewData) {
        Log.d(AlbumListFragment::javaClass.name, "Track clicked")

        tracksViewModel.onSelectedTrack(trackData)
    }

    // this fun appears at AlbumListFragment as well
    // - consider "adding" it to Fragment; as far as I remember, it is possible in Kotlin.
    private fun showFragment(containerViewId: Int, fragment: Fragment, addToBackStack: Boolean = true) {
        fragmentManager?.commit {
            replace(containerViewId, fragment)
            if(addToBackStack) {
                addToBackStack(null)
            }
        }
    }
    private fun loadTracks(albumId: String) {
        requireArguments().getString("albumId")?.let { tracksViewModel.loadTracks(it) }

        tracksViewModel.tracksListBindViewData().observe(viewLifecycleOwner, {
            tracksAdapter.submitList(it)
        })
    }
}