package com.dimhans.dimhansiddapp.progress

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("task_prefs")

class ProgressDataStore(private val context: Context) {

    private val TASK_KEY = intPreferencesKey("completed_tasks")

    // Flow of completed tasks
    val completedTasksFlow: Flow<Int> = context.dataStore.data
        .map { preferences -> preferences[TASK_KEY] ?: 0 }

    // Update completed tasks
    suspend fun saveCompletedTasks(count: Int) {
        context.dataStore.edit { preferences ->
            preferences[TASK_KEY] = count
        }
    }
}
