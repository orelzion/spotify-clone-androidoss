package com.github.orelzion.spotifyclone.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class AlbumsResponseWrapper(val albums: AlbumsResponse): Parcelable

@Parcelize
@Serializable
data class AlbumsResponse(val total: Int, val offset: Int, val items: List<Album>): Parcelable

@Parcelize
@Serializable
data class Album(
    val id: String,
    val artists: List<Artist>,
    val images: List<Image>,
    val name: String,
    @SerialName("total_tracks")
    val totalTracks: Int
): Parcelable

@Parcelize
@Serializable
data class Artist(val id: String, val name: String):Parcelable

@Parcelize
@Serializable
data class Image(val height: Int, val width: Int, val url: String): Parcelable
