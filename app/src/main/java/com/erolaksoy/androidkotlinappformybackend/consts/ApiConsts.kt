package com.erolaksoy.androidkotlinappformybackend.consts

object ApiConsts {
    const val authBaseUrl = "http://localhost/10.0.2.2:5001/"
    const val apiBaseUrl = "http://localhost/10.0.2.2:5000/"
    const val photoApiBaseUrl = "http://localhost/10.0.2.2:5002/"

    // base url must be end / -> Bu hata yüzünden uygulama crash oluyor.
    const val clientCredentialsGrantType="client_credentials"
    const val resourceOwnerPasswordCredentialGrantType="password"
    const val refreshTokenCredentialGrantType = "refresh_token"
}