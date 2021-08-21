package com.joyner.aesdukpt.sp;

import android.content.Context;
import android.content.SharedPreferences;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Log;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import java.io.IOException;
import java.security.GeneralSecurityException;

public final class EncriptedSharedPrefs {

    private SharedPreferences sharedPreferences;

    private EncriptedSharedPrefs(Builder builder) {
        Context context = builder.context;
        String keyStoreAlias = builder.keyStoreAlias;
        int keyStoreSize = builder.keyStoreSize;

        try {
            KeyGenParameterSpec spec = new KeyGenParameterSpec.Builder(
                    keyStoreAlias,
                    KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                    .setKeySize(keyStoreSize)
                    .build();
            MasterKey masterKey = new MasterKey.Builder(context, keyStoreAlias)
                    .setKeyGenParameterSpec(spec)
                    .build();
            sharedPreferences = EncryptedSharedPreferences.create(
                    context,
                    keyStoreAlias,
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (GeneralSecurityException | IOException e) {
            Log.e("EncriptedSharedPrefs", e.getMessage());
        }
    }

    /**
     * Save value and return is successful or not.
     *
     * @param key The key for save value.
     * @param value The value for save.
     * @return returns a boolean indicating if the value is save or not.
     */
    public boolean saveBooleanValue(String key, boolean value) {
        return sharedPreferences.edit().putBoolean(key, value).commit();
    }

    /**
     * Save value and return is successful or not.
     *
     * @param key The key for save value.
     * @param value The value for save.
     * @return returns a boolean indicating if the value is save or not.
     */
    public boolean saveFloatValue(String key, float value) {
        return sharedPreferences.edit().putFloat(key, value).commit();
    }

    /**
     * Save value and return is successful or not.
     *
     * @param key The key for save value.
     * @param value The value for save.
     * @return returns a boolean indicating if the value is save or not.
     */
    public boolean saveIntValue(String key, int value) {
        return sharedPreferences.edit().putInt(key, value).commit();
    }

    /**
     * Save value and return is successful or not.
     *
     * @param key The key for save value.
     * @param value The value for save.
     * @return returns a boolean indicating if the value is save or not.
     */
    public boolean saveLongValue(String key, long value) {
        return sharedPreferences.edit().putLong(key, value).commit();
    }

    /**
     * Save value and return is successful or not.
     *
     * @param key The key for save value.
     * @param value The value for save.
     * @return returns a boolean indicating if the value is save or not.
     */
    public boolean saveStringValue(String key, String value) {
        return sharedPreferences.edit().putString(key, value).commit();
    }

    /**
     * Get value and return boolean.
     *
     * @param key The key for get value.
     * @return return a boolean value, default value is false.
     */
    public boolean getBooleanValue(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    /**
     * Get value and return float.
     *
     * @param key The key for get value.
     * @return return a float value, default value is -1.
     */
    public float getFloatValue(String key) {
        return sharedPreferences.getFloat(key, -1);
    }

    /**
     * Get value and return int.
     *
     * @param key The key for get value.
     * @return return a int value, default value is -1.
     */
    public int getIntValue(String key) {
        return sharedPreferences.getInt(key, -1);
    }

    /**
     * Get value and return long.
     *
     * @param key The key for get value.
     * @return return a long value, default value is -1.
     */
    public long getLongValue(String key) {
        return sharedPreferences.getLong(key, -1);
    }

    /**
     * Get value and return String.
     *
     * @param key The key for get value.
     * @return return a String value, default value is null.
     */
    public String getStringValue(String key) {
        return sharedPreferences.getString(key, null);
    }

    /**
     * Remove value and return is successful or not.
     *
     * @param key The key for save value.
     * @return returns a boolean indicating if the value is removed or not.
     */
    public boolean removeValue(String key) {
        return sharedPreferences.edit().remove(key).commit();
    }

    public static class Builder {

        private final Context context;
        private final String keyStoreAlias;
        private final int keyStoreSize;

        /**
         * Constructor.
         *
         * @param context The application context.
         * @param keyStoreAlias The String key alias value of .jks file for app signing.
         * @param keyStoreSize The int key alias size of .jks file for app signing. Send 256 by default
         */
        public Builder(Context context, String keyStoreAlias, int keyStoreSize) {
            this.context = context;
            this.keyStoreAlias = keyStoreAlias;
            this.keyStoreSize = keyStoreSize;
        }

        /**
         * Builds the Encripted Shared Preferences.
         *
         * @return the Encripted Shared Preferences.
         */

        public EncriptedSharedPrefs build() {
            return new EncriptedSharedPrefs(this);
        }
    }
}
