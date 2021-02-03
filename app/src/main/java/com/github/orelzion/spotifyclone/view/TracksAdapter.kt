package com.github.orelzion.spotifyclone.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.orelzion.spotifyclone.R
import com.github.orelzion.spotifyclone.viewmodel.TrackViewData

class TracksAdapter : ListAdapter<TrackViewData, TracksAdapter.ViewHolder>(TracksDiffUtil()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TracksAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_song_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val trackNumber: TextView = view.findViewById(R.id.trackNumber)
        private val trackName: TextView = view.findViewById(R.id.trackName)
        private val trackDuration: TextView = view.findViewById(R.id.trackDuration)

        fun bind(trackViewData: TrackViewData) {
            trackNumber.text = trackViewData.trackNumber.toString()
            trackName.text = trackViewData.name
            trackDuration.text = trackViewData.duration.toString()
        }
    }

    // TODO: Ask Orel - it looks like duplicated code - the same as in AlbumsAdapter.kt
    class TracksDiffUtil : DiffUtil.ItemCallback<TrackViewData>(){
        override fun areItemsTheSame(oldItem: TrackViewData, newItem: TrackViewData): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: TrackViewData, newItem: TrackViewData): Boolean {
            return oldItem == newItem
        }

    }
}
