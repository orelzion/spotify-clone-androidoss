package com.github.orelzion.spotifyclone.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class TracksResponse(val tracks: Tracks) : Parcelable

@Parcelize
@Serializable
data class Tracks(val items: List<Track>) : Parcelable

@Parcelize
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
) : Parcelable

@Parcelize
@Serializable
data class ExternalUrl(val spotify: String) : Parcelable
