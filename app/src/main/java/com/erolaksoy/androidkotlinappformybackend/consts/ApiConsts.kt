package com.erolaksoy.androidkotlinappformybackend.consts

object ApiConsts {
    //genymotion 10.0.3.2
    //const val authBaseUrl = "http://localhost/10.0.3.2:5001/"
    const val authBaseUrl = "http://10.0.3.2:5001/"
    const val apiBaseUrl = "http://10.0.3.2:5000/"
    const val photoApiBaseUrl = "http://10.0.3.2:5002/"

    // base url must be end / -> Bu hata yüzünden uygulama crash oluyor.
    const val clientCredentialsGrantType="client_credentials"
    const val resourceOwnerPasswordCredentialGrantType="password"
    const val refreshTokenCredentialGrantType = "refresh_token"
}