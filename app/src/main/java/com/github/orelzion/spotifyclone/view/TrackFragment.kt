package com.github.orelzion.spotifyclone.view

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.github.orelzion.spotifyclone.R
import com.github.orelzion.spotifyclone.viewmodel.TracksViewModel

class TrackFragment : Fragment(R.layout.fragment_track_item) {

//    private val selectedTrackView: TextView by lazy { requireView().findViewById(R.id.selectedTrackContainer) }

    private val selectedFragmentName: TextView by lazy { requireView().findViewById(R.id.trackName) }

    private val selectedTrackViewModel by activityViewModels<TracksViewModel>()

    companion object {
        fun newInstance() : TrackFragment {
            return TrackFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        selectedTrackViewModel.selectedTrackBindViewData().observe(viewLifecycleOwner) {
            selectedFragmentName.text = it.name
        }
    }
}