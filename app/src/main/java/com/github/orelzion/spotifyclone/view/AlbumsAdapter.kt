package com.github.orelzion.spotifyclone.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.orelzion.spotifyclone.R
import com.github.orelzion.spotifyclone.viewmodel.AlbumViewData

class AlbumsAdapter : ListAdapter<AlbumViewData, AlbumsAdapter.ViewHolder>(AlbumsDiffUtil()) {

    var clickListener: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumsAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_album_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))

        holder.itemView.setOnClickListener {
            clickListener?.invoke(getItem(position).id)
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // TODO: Is albumsArtView redundant?
        // private val albumsArtView: ImageView = view.findViewById(R.id.albumArtView)
        private val albumTitleView: TextView = view.findViewById(R.id.albumTitleView)
        private val artistNameView: TextView = view.findViewById(R.id.artistNameView)
        private val totalTracksView: TextView = view.findViewById(R.id.totalTracksView)

        fun bind(albumViewData: AlbumViewData) {
            albumTitleView.text = albumViewData.title
            artistNameView.text = albumViewData.artistName
            totalTracksView.text = itemView.context
                .getString(R.string.total_tracks, albumViewData.totalTracks)
        }
    }

    class AlbumsDiffUtil : DiffUtil.ItemCallback<AlbumViewData>() {
        override fun areItemsTheSame(oldItem: AlbumViewData, newItem: AlbumViewData): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: AlbumViewData, newItem: AlbumViewData): Boolean {
            return oldItem == newItem
        }

    }
}