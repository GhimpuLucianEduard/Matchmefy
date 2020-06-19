package com.lucianghimpu.matchmefy.services

import com.lucianghimpu.matchmefy.utilities.Extensions.empty
import kotlin.reflect.KClass

interface EncryptedSharedPreferencesService {
    fun addPreference(key: String, value: String)
    fun getPreference(key: String, defaultValue: String = String.empty): String

    /**
     * Adds an object in the shared preferences for a given key
     *
     * @param T the type of the object
     * @param key the key
     * @param value the value of the object
     */
    fun <T> addPreference(key: String, value: T)

    /**
     * Gets an object for a given key, stored in the shared preferences
     * This is using KClass since inline is not supported in a interface
     *
     * @param T the type of the object
     * @param key the key
     * @param objectClass the KClass of T
     *
     * @return the object stored or null
     */
    fun <T: Any> getPreference(key: String, objectClass: KClass<T>): T?
}