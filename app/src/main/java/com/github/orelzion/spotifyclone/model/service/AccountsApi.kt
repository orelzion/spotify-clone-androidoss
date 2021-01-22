package com.github.orelzion.spotifyclone.model.service

import com.github.orelzion.spotifyclone.model.response.TokenResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AccountsApi {
    @FormUrlEncoded
    @POST("token")
    fun refreshToken(@Field("grant_type") grantType: String): Call<TokenResponse>
}