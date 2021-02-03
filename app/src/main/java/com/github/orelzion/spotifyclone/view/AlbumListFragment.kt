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
import com.github.orelzion.spotifyclone.model.AlbumsResponse
import com.github.orelzion.spotifyclone.model.repository.BrowseRepository
import com.github.orelzion.spotifyclone.viewmodel.AlbumViewData

/**
 * A fragment representing a list of Items.
 */
class AlbumListFragment : Fragment(R.layout.fragment_items_list) {

    private val albumsListView: RecyclerView by lazy { requireView().findViewById(R.id.itemsList) }
    private val albumsAdapter: AlbumsAdapter by lazy { AlbumsAdapter() }

    private val progressBar: ProgressBar by lazy { requireView().findViewById(R.id.progressBar) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        albumsListView.adapter = albumsAdapter.apply {
            clickListener = this@AlbumListFragment::onItemClicked
        }
//        albumsListView.layoutManager = LinearLayoutManager(requireContext())

        if (savedInstanceState == null) {
            loadAlbums()
        }

        progressBar.isVisible = false
    }

    private fun onItemClicked(albumId: String) {
        // TODO: Understand how to call loadTracks
//        loadTracks(albumId)
    }


    private fun loadAlbums() {
        BrowseRepository.fetchNewReleases { response, t ->
            if (response != null) {
                albumsAdapter.submitList(responseToAlbumsData(response))
            } else {
                Toast.makeText(requireContext(), R.string.loading_error, Toast.LENGTH_LONG).show()
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