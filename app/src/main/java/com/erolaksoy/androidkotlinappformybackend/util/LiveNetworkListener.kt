package com.erolaksoy.androidkotlinappformybackend.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class LiveNetworkListener {
    companion object {
        private fun getConnectionType(context: Context): Int {
            var result = 0
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            connectivityManager.run {
                this.getNetworkCapabilities(this.activeNetwork)?.let {
                    if (it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) result = 1
                    if (it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) result = 2
                }
            }

            return result
        }

        fun isConnected(): Boolean {
            val context = GlobalApp.getAppContext()
            val connectionType = getConnectionType(context)
            return connectionType!=0
        }
    }
}