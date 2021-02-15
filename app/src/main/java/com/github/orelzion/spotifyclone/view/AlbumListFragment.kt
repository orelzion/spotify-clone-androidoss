package com.github.orelzion.spotifyclone.view

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
//import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.recyclerview.widget.RecyclerView
import com.github.orelzion.spotifyclone.R
//import com.github.orelzion.spotifyclone.model.AlbumsResponse
//import com.github.orelzion.spotifyclone.model.repository.BrowseRepository
//import com.github.orelzion.spotifyclone.viewmodel.AlbumViewData
import com.github.orelzion.spotifyclone.viewmodel.AlbumsViewModel


/**
 * A fragment representing a list of Items.
 */
class AlbumListFragment : Fragment(R.layout.fragment_items_list) {

    private val albumsListView: RecyclerView by lazy { requireView().findViewById(R.id.itemsList) }
    private val albumsAdapter: AlbumsAdapter by lazy { AlbumsAdapter() }

    private val albumsViewModel by activityViewModels<AlbumsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         * Set albumAdapter's clickListener to AlbumListFragment's onItemClicked method
         */
        albumsListView.adapter = albumsAdapter.apply {
            clickListener = this@AlbumListFragment::onItemClicked
        }

        /**
         * (re-)load Albums - depends on it's availability
         */
        if (savedInstanceState == null) {
            loadAlbums()
        }
        // TODO: add else - restore savedInstanceState rather than (re-)loadAlbums

        requireView().findViewById<ProgressBar>(R.id.progressBar).isVisible = false
    }

    /**
     * onItemClicked - show corresponding tracks' list in main fragment
     */
    private fun onItemClicked(albumId: String) {
        showFragment(R.id.mainFragmentContainer, TracksListFragment.newInstance(albumId), true)
    }

    private fun showFragment(containerViewId: Int, fragment: Fragment, addToBackStack: Boolean = true) {
        fragmentManager?.commit {
            replace(containerViewId, fragment)
            if(addToBackStack) {
                addToBackStack(null)
            }
        }
    }

    private fun loadAlbums() {
        albumsViewModel.loadAlbums()

        albumsViewModel.bindViewData().observe(viewLifecycleOwner, {
            albumsAdapter.submitList(it)
        })
    }

}