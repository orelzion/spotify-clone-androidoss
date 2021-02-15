package com.github.orelzion.spotifyclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import com.github.orelzion.spotifyclone.view.AlbumListFragment
import com.github.orelzion.spotifyclone.view.TrackFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.commit {
            replace(R.id.mainFragmentContainer, AlbumListFragment())
            replace(R.id.selectedTrackContainer, TrackFragment.newInstance())
        }
    }
}