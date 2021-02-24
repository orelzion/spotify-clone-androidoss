package com.github.orelzion.spotifyclone

import androidx.fragment.app.Fragment
import androidx.fragment.app.commit

object ClassesExtentions {
    fun Fragment.showFragment(
        containerViewId: Int,
        fragment: Fragment,
        addToBackStack: Boolean = true
    ) {
        fragmentManager?.commit {
            replace(containerViewId, fragment)
            if (addToBackStack)
                addToBackStack(null)
        }
    }
}