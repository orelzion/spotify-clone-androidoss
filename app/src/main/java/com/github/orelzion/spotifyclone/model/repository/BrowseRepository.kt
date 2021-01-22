package com.github.orelzion.spotifyclone.model.repository

import android.util.Log
import com.github.orelzion.spotifyclone.model.AlbumsResponse
import com.github.orelzion.spotifyclone.model.AlbumsResponseWrapper
import com.github.orelzion.spotifyclone.model.RetrofitCreator
import com.github.orelzion.spotifyclone.model.TracksResponse
import com.github.orelzion.spotifyclone.model.service.BrowseApi
import okhttp3.Interceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object BrowseRepository {

    private const val BASE_URL = "https://api.spotify.com/v1/"
    private var lastItemCount = 0

    private val browseApi: BrowseApi by lazy {
        RetrofitCreator
            .createService(
                BASE_URL,
                OAuthInterceptor()
            )
    }

    private class OAuthInterceptor() : Interceptor {
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            var request = chain.request()
            request = request
                .newBuilder()
                .header(
                    "Authorization",
                    "${TokenRepository.tokenType} ${TokenRepository.accessToken}"
                )
                .build()

            return chain.proceed(request)
        }
    }

    fun fetchNewReleases(
        refresh: Boolean = false,
        callback: (response: AlbumsResponse?, t: Throwable?) -> Unit
    ) {
        if (TokenRepository.accessToken == null) {
            TokenRepository.refreshToken { success, t ->
                if (success) {
                    fetchNewReleases(callback = callback)
                } else {
                    Log.e(BrowseRepository::class.java.simpleName, "failed to fetch token", t)
                }
            }
            return
        }

        if (refresh) {
            lastItemCount = 0
        }
        browseApi
            .newRelease(lastItemCount, 20)
            .enqueue(NewReleasesCallback(callback))
    }

    private class NewReleasesCallback(private val callback: (response: AlbumsResponse?, t: Throwable?) -> Unit) :
        Callback<AlbumsResponseWrapper> {
        override fun onResponse(call: Call<AlbumsResponseWrapper>, response: Response<AlbumsResponseWrapper>) {
            if (response.isSuccessful) {
                response.body()?.albums?.let {
                    lastItemCount = it.offset

                    // Inform the caller of a successful response
                    callback.invoke(it, null)
                    return
                }
            } else if (response.code() == 401) {
                // Refresh token and try again
                TokenRepository.refreshToken { success, t ->
                    if (success) {
                        fetchNewReleases(callback = callback)
                    } else {
                        Log.e(BrowseRepository::class.java.simpleName, "failed to fetch token", t)
                    }
                }
                return
            }

            // Inform the caller on a failure
            callback.invoke(null, Exception(response.errorBody()?.string()))
        }

        override fun onFailure(call: Call<AlbumsResponseWrapper>, t: Throwable) {
            // Inform the caller on a failure
            callback.invoke(null, t)
        }

    }

    fun fetchAlbumDetails(
        albumId: String,
        callback: (response: TracksResponse?, t: Throwable?) -> Unit
    ) {
        browseApi
            .albums(albumId)
            .enqueue(TracksCallback(albumId, callback))
    }

    private class TracksCallback(
        private val albumId: String,
        private val callback: (response: TracksResponse?, t: Throwable?) -> Unit
    ) : Callback<TracksResponse> {
        override fun onResponse(call: Call<TracksResponse>, response: Response<TracksResponse>) {
            if (response.isSuccessful) {
                response.body()?.let {
                    // Inform the caller of a successful response
                    callback.invoke(it, null)
                    return
                }
            } else if (response.code() == 401) {
                // Refresh token and try again
                TokenRepository.refreshToken { success, t ->
                    if (success) {
                        fetchAlbumDetails(albumId, callback)
                    } else {
                        Log.e(BrowseRepository::class.java.simpleName, "failed to fetch token", t)
                    }
                }
                return
            }

            // Inform the caller on a failure
            callback.invoke(null, Exception(response.errorBody()?.string()))
        }

        override fun onFailure(call: Call<TracksResponse>, t: Throwable) {
            // Inform the caller on a failure
            callback.invoke(null, t)
        }

    }
}