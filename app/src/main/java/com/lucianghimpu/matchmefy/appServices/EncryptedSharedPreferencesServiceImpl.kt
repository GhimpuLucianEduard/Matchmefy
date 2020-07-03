package com.lucianghimpu.matchmefy.appServices

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.google.gson.GsonBuilder
import com.lucianghimpu.matchmefy.utilities.PreferencesConstants.PREFERENCES_FILE_KEY
import kotlin.reflect.KClass

class EncryptedSharedPreferencesServiceImpl(
    context: Context
) : EncryptedSharedPreferencesService {

    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    private val sharedPreferences = EncryptedSharedPreferences.create(
        PREFERENCES_FILE_KEY,
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    override fun addPreference(key: String, value: String) {
        sharedPreferences.edit()
            .putString(key, value)
            .apply()
    }

    override fun getPreference(key: String, defaultValue: String): String {
        return sharedPreferences.getString(key, defaultValue)!!
    }

    override fun <T> addPreference(key: String, value: T) {
        val json = GsonBuilder().create().toJson(value)
        sharedPreferences.edit()
            .putString(key, json)
            .apply()
    }

    override fun <T: Any> getPreference(key: String, objectClass: KClass<T>): T? {
        val value = sharedPreferences.getString(key, null)
        return GsonBuilder().create().fromJson(value, objectClass.java)
    }
}