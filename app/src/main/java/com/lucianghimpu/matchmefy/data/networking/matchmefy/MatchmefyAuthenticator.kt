package com.lucianghimpu.matchmefy.data.networking.matchmefy

import com.lucianghimpu.matchmefy.appServices.AppAnalytics
import com.lucianghimpu.matchmefy.appServices.PreferencesService
import com.lucianghimpu.matchmefy.data.dataModels.matchmefyAPI.MatchmefyRefreshToken
import com.lucianghimpu.matchmefy.utilities.PreferencesConstants
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import org.json.JSONObject
import timber.log.Timber

class MatchmefyAuthenticator(
    private val sharedPreferencesService: PreferencesService,
    private val matchmefyRefreshTokenServiceFactory: MatchmefyRefreshTokenServiceFactory
) : Authenticator {

    private var matchmefyRefreshTokenApiService : MatchmefyRefreshTokenApiService = matchmefyRefreshTokenServiceFactory.create(
        MatchmefyRefreshTokenApiService::class.java)

    override fun authenticate(route: Route?, response: Response): Request? {
        var ce = sharedPreferencesService.getString(PreferencesConstants.MATCHMEFY_REFRESH_TOKEN_KEY)
        val refreshTokenResponse = matchmefyRefreshTokenApiService.refreshToken(
            MatchmefyRefreshToken(sharedPreferencesService.getString("ss"))
        ).execute()

        if (refreshTokenResponse.isSuccessful) {
            Timber.d( "Matchmefy token refreshed successfully")

            sharedPreferencesService.addString(
                PreferencesConstants.MATCHMEFY_ACCESS_TOKEN_KEY,
                refreshTokenResponse.body()!!.accessToken,
                true)

            return response.request.newBuilder()
                .header("Authorization", "Bearer ${refreshTokenResponse.body()!!.accessToken}")
                .build()
        }
        val jObjError = JSONObject (refreshTokenResponse.errorBody()!!.string())
        AppAnalytics.trackError(Exception(jObjError.toString()), "Failed to refresh Matchmefy Token")
        return null
    }
}