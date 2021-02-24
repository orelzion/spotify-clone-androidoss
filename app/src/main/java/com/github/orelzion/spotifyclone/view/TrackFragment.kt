package com.github.orelzion.spotifyclone.view

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.github.orelzion.spotifyclone.R
import com.github.orelzion.spotifyclone.viewmodel.TracksViewModel

class TrackFragment : Fragment(R.layout.fragment_track_item) {


    /**
     * Reference to the TextView that display track's details
     */
    private val trackNameView: TextView by lazy { requireView().findViewById(R.id.trackName) }

    /**
     * Reference to the corresponding ViewModel
     */
    private val selectedTrackViewModel by activityViewModels<TracksViewModel>()

    companion object {
        fun newInstance() : TrackFragment {
            return TrackFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         * "observe" the ViewModel, and update TextView by it.
         */
        selectedTrackViewModel.selectedTrackLiveData.observe(viewLifecycleOwner) {
            trackNameView.text = "${it.trackNumber}\t\t\t${it.name}\t\t\t${it.duration}"
        }
    }
}