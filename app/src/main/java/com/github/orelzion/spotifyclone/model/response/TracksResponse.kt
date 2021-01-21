package com.github.orelzion.spotifyclone.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TracksResponse(val tracks: Tracks)

@Serializable
data class Tracks(val items: List<Track>)

@Serializable
data class Track(
    @SerialName("external_urls")
    val externalUrl: ExternalUrl,
    @SerialName("duration_ms")
    val duration: Long,
    val name: String,
    val id: String,
    @SerialName("track_number")
    val trackNumber: Int
)

@Serializable
data class ExternalUrl(val spotify: String)
