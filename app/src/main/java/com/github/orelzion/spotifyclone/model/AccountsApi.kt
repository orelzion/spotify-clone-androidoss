package com.github.orelzion.spotifyclone.model

import com.github.orelzion.spotifyclone.model.request.TokenRefreshReqBody
import com.github.orelzion.spotifyclone.model.response.TokenResponse
import retrofit2.Callback
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AccountsApi {
    @POST("token")
    fun refreshToken(@Body tokenRefreshReqBody: TokenRefreshReqBody): Callback<TokenResponse>
}