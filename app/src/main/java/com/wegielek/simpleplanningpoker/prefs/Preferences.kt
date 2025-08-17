package com.wegielek.simpleplanningpoker.prefs

import android.content.Context
import android.util.Base64
import android.util.Log
import androidx.core.content.edit
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

class Preferences {
    companion object {
        private val Context.dataStore by preferencesDataStore("secure_prefs")
        private val TOKEN_KEY = stringPreferencesKey("access_token")

        // For demo: store key in Android Keystore
        private fun getOrCreateSecretKey(context: Context): SecretKey {
            val masterKey =
                MasterKey
                    .Builder(context)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build()
            // In real apps, generate AES key with KeyGenerator and wrap it with masterKey
            val keyGen = KeyGenerator.getInstance("AES")
            keyGen.init(256)
            return keyGen.generateKey()
        }

        private fun encrypt(
            value: String,
            secretKey: SecretKey,
        ): String {
            val cipher = Cipher.getInstance("AES/GCM/NoPadding")
            cipher.init(Cipher.ENCRYPT_MODE, secretKey)
            val iv = cipher.iv
            val encrypted = cipher.doFinal(value.toByteArray())
            return Base64.encodeToString(iv + encrypted, Base64.DEFAULT)
        }

        private fun decrypt(
            value: String,
            secretKey: SecretKey,
        ): String {
            val bytes = Base64.decode(value, Base64.DEFAULT)
            val iv = bytes.copyOfRange(0, 12)
            val encrypted = bytes.copyOfRange(12, bytes.size)

            val cipher = Cipher.getInstance("AES/GCM/NoPadding")
            cipher.init(Cipher.DECRYPT_MODE, secretKey, GCMParameterSpec(128, iv))
            return String(cipher.doFinal(encrypted))
        }

        suspend fun saveToken(
            context: Context,
            token: String,
        ) {
            val secretKey = getOrCreateSecretKey(context)
            val encrypted = encrypt(token, secretKey)
            context.dataStore.edit { prefs ->
                prefs[TOKEN_KEY] = encrypted
            }
        }

        fun getToken(context: Context): Flow<String?> {
            val secretKey = getOrCreateSecretKey(context)
            return context.dataStore.data.map { prefs ->
                prefs[TOKEN_KEY]?.let { decrypt(it, secretKey) }
            }
        }

        suspend fun getTokenOnce(context: Context): String? {
            val secretKey = getOrCreateSecretKey(context)
            return context.dataStore.data
                .map { prefs ->
                    prefs[TOKEN_KEY]?.let { decrypt(it, secretKey) }
                }.firstOrNull()
        }

        suspend fun clearToken(context: Context) {
            context.dataStore.edit { prefs ->
                prefs.remove(TOKEN_KEY)
            }
        }

        fun saveRoomCodeToStorage(
            context: Context,
            roomCode: String?,
        ) {
            val prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
            prefs.edit {
                putString("room_code", roomCode)
            }
        }

        fun getRoomCodeFromStorage(context: Context): String? {
            val prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
            return prefs.getString("room_code", null)
        }

        fun clearRoomCodeFromStorage(context: Context) {
            val prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
            prefs.edit {
                remove("room_code")
            }
        }
    }
}
