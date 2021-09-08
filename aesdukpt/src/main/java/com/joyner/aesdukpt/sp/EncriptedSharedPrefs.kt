package com.joyner.aesdukpt.sp

import android.content.Context
import android.content.SharedPreferences
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Log
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import java.io.IOException
import java.security.GeneralSecurityException

class EncriptedSharedPrefs private constructor(builder: Builder) {
    private var sharedPreferences: SharedPreferences? = null

    /**
     * Save value and return is successful or not.
     *
     * @param key The key for save value.
     * @param value The value for save.
     * @return returns a boolean indicating if the value is save or not.
     */
    fun saveBooleanValue(key: String?, value: Boolean): Boolean {
        return sharedPreferences!!.edit().putBoolean(key, value).commit()
    }

    /**
     * Save value and return is successful or not.
     *
     * @param key The key for save value.
     * @param value The value for save.
     * @return returns a boolean indicating if the value is save or not.
     */
    fun saveFloatValue(key: String?, value: Float): Boolean {
        return sharedPreferences!!.edit().putFloat(key, value).commit()
    }

    /**
     * Save value and return is successful or not.
     *
     * @param key The key for save value.
     * @param value The value for save.
     * @return returns a boolean indicating if the value is save or not.
     */
    fun saveIntValue(key: String?, value: Int): Boolean {
        return sharedPreferences!!.edit().putInt(key, value).commit()
    }

    /**
     * Save value and return is successful or not.
     *
     * @param key The key for save value.
     * @param value The value for save.
     * @return returns a boolean indicating if the value is save or not.
     */
    fun saveLongValue(key: String?, value: Long): Boolean {
        return sharedPreferences!!.edit().putLong(key, value).commit()
    }

    /**
     * Save value and return is successful or not.
     *
     * @param key The key for save value.
     * @param value The value for save.
     * @return returns a boolean indicating if the value is save or not.
     */
    fun saveStringValue(key: String?, value: String?): Boolean {
        return sharedPreferences!!.edit().putString(key, value).commit()
    }

    /**
     * Get value and return boolean.
     *
     * @param key The key for get value.
     * @return return a boolean value, default value is false.
     */
    fun getBooleanValue(key: String?): Boolean {
        return sharedPreferences!!.getBoolean(key, false)
    }

    /**
     * Get value and return float.
     *
     * @param key The key for get value.
     * @return return a float value, default value is -1.
     */
    fun getFloatValue(key: String?): Float {
        return sharedPreferences!!.getFloat(key, -1f)
    }

    /**
     * Get value and return int.
     *
     * @param key The key for get value.
     * @return return a int value, default value is -1.
     */
    fun getIntValue(key: String?): Int {
        return sharedPreferences!!.getInt(key, -1)
    }

    /**
     * Get value and return long.
     *
     * @param key The key for get value.
     * @return return a long value, default value is -1.
     */
    fun getLongValue(key: String?): Long {
        return sharedPreferences!!.getLong(key, -1)
    }

    /**
     * Get value and return String.
     *
     * @param key The key for get value.
     * @return return a String value, default value is null.
     */
    fun getStringValue(key: String?): String? {
        return sharedPreferences!!.getString(key, null)
    }

    /**
     * Remove value and return is successful or not.
     *
     * @param key The key for save value.
     * @return returns a boolean indicating if the value is removed or not.
     */
    fun removeValue(key: String?): Boolean {
        return sharedPreferences!!.edit().remove(key).commit()
    }

    class Builder
    /**
     * Constructor.
     *
     * @param context The application context.
     * @param keyStoreAlias The String key alias value of .jks file for app signing.
     * @param keyStoreSize The int key alias size of .jks file for app signing. Send 256 by default
     */(val context: Context, val keyStoreAlias: String, val keyStoreSize: Int) {
        /**
         * Builds the Encripted Shared Preferences.
         *
         * @return the Encripted Shared Preferences.
         */
        fun build(): EncriptedSharedPrefs {
            return EncriptedSharedPrefs(this)
        }
    }

    init {
        val context = builder.context
        val keyStoreAlias = builder.keyStoreAlias
        val keyStoreSize = builder.keyStoreSize
        try {
            val spec = KeyGenParameterSpec.Builder(
                    keyStoreAlias,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                    .setKeySize(keyStoreSize)
                    .build()
            val masterKey = MasterKey.Builder(context, keyStoreAlias)
                    .setKeyGenParameterSpec(spec)
                    .build()
            sharedPreferences = EncryptedSharedPreferences.create(
                    context,
                    keyStoreAlias,
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        } catch (e: GeneralSecurityException) {
            Log.e("EncriptedSharedPrefs", e.message!!)
        } catch (e: IOException) {
            Log.e("EncriptedSharedPrefs", e.message!!)
        }
    }
}