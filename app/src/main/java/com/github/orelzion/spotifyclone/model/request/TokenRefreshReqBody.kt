package com.github.orelzion.spotifyclone.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenRefreshReqBody(
    @SerialName("grant_type")
    val grantType: String = "client_credentials"
)
