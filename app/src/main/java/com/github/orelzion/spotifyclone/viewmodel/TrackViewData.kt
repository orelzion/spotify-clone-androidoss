package com.github.orelzion.spotifyclone.viewmodel

// TODO: Ask orel if that import is the right way to refer to ExternalUrl?
import com.github.orelzion.spotifyclone.model.ExternalUrl

data class TrackViewData (
    val id: String,
    val name: String,
    val duration: Long,
    val trackNumber: Int,
    val externalUrl: ExternalUrl
)
