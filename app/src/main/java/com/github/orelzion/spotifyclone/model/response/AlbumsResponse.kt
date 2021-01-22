package com.github.orelzion.spotifyclone.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AlbumsResponseWrapper(val albums: AlbumsResponse)

@Serializable
data class AlbumsResponse(val total: Int, val offset: Int, val items: List<Album>)

@Serializable
data class Album(
    val id: String,
    val artists: List<Artist>,
    val images: List<Image>,
    val name: String,
    @SerialName("total_tracks")
    val totalTracks: Int
)

@Serializable
data class Artist(val id: String, val name: String)

@Serializable
data class Image(val height: Int, val width: Int, val url: String)
