package com.dimhans.dimhansiddapp

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

class PreferencesManager(context: Context) {
    private val dataStore = context.dataStore

    companion object {
        val SAVED_EMAIL = stringPreferencesKey("saved_email")
        val SAVED_PASSWORD = stringPreferencesKey("saved_password")
    }

    suspend fun saveCredentials(email: String, password: String) {
        dataStore.edit { prefs ->
            prefs[SAVED_EMAIL] = email
            prefs[SAVED_PASSWORD] = password
        }
    }

    suspend fun clearCredentials() {
        dataStore.edit { prefs ->
            prefs.remove(SAVED_EMAIL)
            prefs.remove(SAVED_PASSWORD)
        }
    }

    val savedEmailFlow: Flow<String?> = dataStore.data.map { it[SAVED_EMAIL] }
    val savedPasswordFlow: Flow<String?> = dataStore.data.map { it[SAVED_PASSWORD] }
}