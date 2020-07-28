package com.lucianghimpu.matchmefy.appServices

import com.lucianghimpu.matchmefy.utilities.Extensions.empty
import kotlin.reflect.KClass

interface EncryptedSharedPreferencesService {
    fun addString(key: String, value: String, useCommit: Boolean = false)
    fun getString(key: String, defaultValue: String = String.empty): String
    fun deleteAll()

    /**
     * Adds an object in the shared preferences for a given key
     *
     * @param T the type of the object
     * @param key the key
     * @param value the value of the object
     */
    fun <T> addObject(key: String, value: T, useCommit: Boolean = false)

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
    fun <T: Any> getObject(key: String, objectClass: KClass<T>): T?
}