package com.github.orelzion.spotifyclone.model

import retrofit2.Callback
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BrowseApi {

    @GET("browse/new-releases")
    fun newRelease(@Query("offset") offset: Int, @Query("limit") limit: Int): Callback<AlbumsResponse>

    @GET("albums/{id}")
    fun albums(@Path("id") albumId: String): Callback<TracksResponse>
}