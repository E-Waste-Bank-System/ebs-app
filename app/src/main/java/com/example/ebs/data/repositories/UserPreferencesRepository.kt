package com.example.ebs.data.repositories

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class UserPreferencesRepository(
    private val dataStore: DataStore<Preferences>
){
    private companion object {
        val AUTH_TOKEN_KEY = stringPreferencesKey("auth_token_key")
        val GAMBAR = stringPreferencesKey("gambar")
        val NAMA = stringPreferencesKey("nama")
        const val TAG = "APP_MEMORY"
    }
    suspend fun saveName(nama: String?) {
        dataStore.edit { preferences ->
            preferences[NAMA] = nama ?: ""
        }
    }

    suspend fun resetName() {
        saveName(null)
    }

    val name: Flow<String?> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e(TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[NAMA]
        }

    suspend fun saveAuthToken(token: String?) {
        dataStore.edit { preferences ->
            preferences[AUTH_TOKEN_KEY] = token ?: ""
        }
    }

    suspend fun resetAuthToken() {
        saveAuthToken(null)
    }

    val authToken: Flow<String?> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e(TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[AUTH_TOKEN_KEY]
        }

    suspend fun saveGambar(token: String?) {
        dataStore.edit { preferences ->
            preferences[GAMBAR] = token ?: ""
        }
    }

    suspend fun resetGambar() {
        saveGambar(null)
    }

    val gambar: Flow<String?> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e(TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[GAMBAR]
        }
}