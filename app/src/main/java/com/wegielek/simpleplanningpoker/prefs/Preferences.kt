package com.wegielek.simpleplanningpoker.prefs

import android.content.Context
import android.util.Log
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

@Suppress("DEPRECATION")
class Preferences {
    companion object {
        fun getTokenFromStorage(context: Context): String? {
            val masterKey =
                MasterKey
                    .Builder(context)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build()

            val prefs =
                EncryptedSharedPreferences.create(
                    context,
                    "secure_prefs",
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
                )

            return prefs.getString("access_token", null)
        }

        fun saveTokenToStorage(
            context: Context,
            token: String?,
        ) {
            if (token == null) {
                Log.e("Preferences", "Access token is null")
                return
            }
            val masterKey =
                MasterKey
                    .Builder(context)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build()

            val prefs =
                EncryptedSharedPreferences.create(
                    context,
                    "secure_prefs",
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
                )

            prefs.edit { putString("access_token", token) }
        }
    }
}
