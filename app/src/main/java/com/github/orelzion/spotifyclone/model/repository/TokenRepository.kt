package com.github.orelzion.spotifyclone.model.repository

import com.github.orelzion.spotifyclone.model.RetrofitCreator
import com.github.orelzion.spotifyclone.model.response.TokenResponse
import com.github.orelzion.spotifyclone.model.service.AccountsApi
import okhttp3.Interceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object TokenRepository {

    private const val BASE_URL = "https://accounts.spotify.com/api/"

    var accessToken: String? = null
    var tokenType: String? = null

    private val accountsApi: AccountsApi by lazy {
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
                    "Basic OWIwNzk1MjdiNGRmNGIwNDg5MzgzMWUyOTgxZDgxMjk6Yjk2M2YwNDNkODYwNGVmYWEwM2Y1YTAxMWQ3YWJlYmQ="
                )
                .build()

            return chain.proceed(request)
        }
    }

    fun refreshToken(successListener: (success: Boolean, t: Throwable?) -> Unit) {
        val call = accountsApi.refreshToken(grantType = "client_credentials")
        call.enqueue(TokenCallback(successListener))
    }

    class TokenCallback(private val successListener: (success: Boolean, t: Throwable?) -> Unit) :
        Callback<TokenResponse> {
        override fun onResponse(call: Call<TokenResponse>, response: Response<TokenResponse>) {

            // Save access token
            if (response.isSuccessful) {
                response.body()?.let {
                    accessToken = it.accessToken
                    tokenType = it.tokenType

                    // Inform caller of successful call
                    successListener.invoke(true, null)
                    return
                }
            }

            // Inform caller of a failure
            successListener.invoke(false, null)
        }

        override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
            // Inform caller of a failure
            successListener.invoke(false, t)
        }
    }
}