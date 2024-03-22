package uz.turgunboyevjurabek.camposedatestore.DataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStore(private val context: Context) {
    companion object{
        private val Context.dataStore:DataStore<Preferences> by preferencesDataStore(name = "UserToken",
            produceMigrations = { context ->
                listOf(SharedPreferencesMigration(context,"UserToken"))
            })
        val USER_TOKEN= stringPreferencesKey("user_token")
    }



    val getToken: Flow<String> = context.dataStore.data
        .map { preferences->
            preferences[USER_TOKEN]?: ""
        }
    suspend fun saveToken(name: String){
        context.dataStore.edit { preferences->
            preferences[USER_TOKEN]=name
        }
    }

}