package com.github.orelzion.spotifyclone.model.service

import com.github.orelzion.spotifyclone.model.AlbumsResponse
import com.github.orelzion.spotifyclone.model.AlbumsResponseWrapper
import com.github.orelzion.spotifyclone.model.TracksResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BrowseApi {

    @GET("browse/new-releases")
    fun newRelease(@Query("offset") offset: Int, @Query("limit") limit: Int): Call<AlbumsResponseWrapper>

    @GET("albums/{id}")
    fun albums(@Path("id") albumId: String): Call<TracksResponse>
}