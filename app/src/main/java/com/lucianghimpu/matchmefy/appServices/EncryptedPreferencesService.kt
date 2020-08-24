package com.lucianghimpu.matchmefy.appServices

import android.annotation.SuppressLint
import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.google.gson.GsonBuilder
import com.lucianghimpu.matchmefy.utilities.PreferencesConstants.PREFERENCES_FILE_KEY
import kotlin.reflect.KClass

class EncryptedPreferencesService(
    context: Context
) : PreferencesService {

    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    private val sharedPreferences = EncryptedSharedPreferences.create(
        PREFERENCES_FILE_KEY,
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    @SuppressLint("ApplySharedPref")
    override fun addString(key: String, value: String, useCommit: Boolean) {
        val editor = sharedPreferences.edit()
            .putString(key, value)

        if (useCommit) editor.commit() else editor.apply()
    }

    override fun getString(key: String, defaultValue: String): String {
        return sharedPreferences.getString(key, defaultValue)!!
    }

    @SuppressLint("ApplySharedPref")
    override fun <T> addObject(key: String, value: T, useCommit: Boolean) {
        val json = GsonBuilder().create().toJson(value)
        val editor = sharedPreferences.edit()
            .putString(key, json)

        if (useCommit) editor.commit() else editor.apply()
    }

    override fun <T: Any> getObject(key: String, objectClass: KClass<T>): T? {
        val value = sharedPreferences.getString(key, null)
        return GsonBuilder().create().fromJson(value, objectClass.java)
    }

    @SuppressLint("ApplySharedPref")
    override fun deleteAll() {
        sharedPreferences.edit()
            .clear()
            .commit()
    }
}