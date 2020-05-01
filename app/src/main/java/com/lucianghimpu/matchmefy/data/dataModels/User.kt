package com.lucianghimpu.matchmefy.data.dataModels

import android.os.Build
import androidx.annotation.RequiresApi
import com.lucianghimpu.matchmefy.utilities.Extensions.empty
import java.time.LocalTime

data class User @RequiresApi(Build.VERSION_CODES.O) constructor(
    val display_name : String = "TestUseraa ${LocalTime.now().second}",
    val email : String = String.empty,
    val external_urls : ExternalUrls = ExternalUrls(),
    val followers : Followers = Followers(),
    val href : String = String.empty,
    val id : String = String.empty,
    val images : List<Image> = emptyList(),
    val type : String = String.empty,
    val uri : String = String.empty
)
