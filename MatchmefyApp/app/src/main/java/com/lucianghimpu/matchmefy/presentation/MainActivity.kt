package com.lucianghimpu.matchmefy.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.presentation.services.SpotifyAuthService
import com.spotify.sdk.android.auth.AuthorizationClient
import org.koin.android.ext.android.inject


class MainActivity : AppCompatActivity() {

    private val spotifyAuthService: SpotifyAuthService by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == spotifyAuthService.AUTH_TOKEN_REQUEST_CODE) {
            val response = AuthorizationClient.getResponse(resultCode, data)
            spotifyAuthService.onAuthResponse(response)
        }
    }
}
