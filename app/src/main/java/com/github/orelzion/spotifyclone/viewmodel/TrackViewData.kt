package com.github.orelzion.spotifyclone.viewmodel

import com.github.orelzion.spotifyclone.model.ExternalUrl

data class TrackViewData (
    val id: String,
    val name: String,
    val duration: Long,
    val trackNumber: Int,
    val externalUrl: ExternalUrl
)
