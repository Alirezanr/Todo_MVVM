package dan.nr.myapplication.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferences(context: Context)
{
    private val applicationContext = context.applicationContext
    private val dataStore: DataStore<Preferences> =
            applicationContext.createDataStore(name = "auth_info_data_store")

    val authToken: Flow<String?> = dataStore.data.map { preferences ->
        preferences[AUTH_KEY]
    }

    suspend fun saveAuthToken(authToken: String)
    {
        dataStore.edit { preferences ->
            preferences[AUTH_KEY] = authToken
        }
    }

    suspend fun clear()
    {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object
    {
        private val AUTH_KEY = stringPreferencesKey("auth_key")
    }
}