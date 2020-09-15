package com.lucianghimpu.matchmefy.data.networking.spotify

import com.lucianghimpu.matchmefy.appServices.AppAnalytics
import com.lucianghimpu.matchmefy.appServices.PreferencesService
import com.lucianghimpu.matchmefy.utilities.PreferencesConstants
import com.lucianghimpu.matchmefy.utilities.PreferencesConstants.SPOTIFY_REFRESH_TOKEN_KEY
import com.lucianghimpu.matchmefy.utilities.SpotifyAuthConstants.CLIENT_ID
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import org.json.JSONObject
import timber.log.Timber

class SpotifyAuthenticator(
    private val sharedPreferencesService: PreferencesService,
    spotifyAuthRetrofitServiceFactory: SpotifyAuthRetrofitServiceFactory
) : Authenticator {

    private var spotifyAuthApiService : SpotifyAuthApiService = spotifyAuthRetrofitServiceFactory.create(
        SpotifyAuthApiService::class.java)

    override fun authenticate(route: Route?, response: Response): Request? {

        val refreshTokenResponse = spotifyAuthApiService.refreshToken(
            "refresh_token",
            sharedPreferencesService.getString(SPOTIFY_REFRESH_TOKEN_KEY),
            CLIENT_ID
        ).execute()

        if (refreshTokenResponse.isSuccessful) {
            Timber.d( "Spotify token refreshed successfully")
            sharedPreferencesService.addString(
                PreferencesConstants.SPOTIFY_ACCESS_TOKEN_KEY,
                refreshTokenResponse.body()!!.access_token,
                true)

            sharedPreferencesService.addString(
                SPOTIFY_REFRESH_TOKEN_KEY,
                refreshTokenResponse.body()!!.refresh_token,
                true)

            return response.request.newBuilder()
                .header("Authorization", "Bearer ${refreshTokenResponse.body()!!.access_token}")
                .build()
        }
        val jObjError = JSONObject (refreshTokenResponse.errorBody()!!.string())
        AppAnalytics.trackError(Exception(jObjError.toString()), "Failed to refresh Spotify Token")
        return null
    }
}