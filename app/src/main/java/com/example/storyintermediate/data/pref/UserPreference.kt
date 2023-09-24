package com.example.storyintermediate.data.pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.storyintermediate.api.response.ListStoryItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {

    private val STORIES_KEY = stringPreferencesKey("stories")


    suspend fun getStoredStories(): List<ListStoryItem>? {
        val storiesJson = dataStore.data.map { preferences ->
            preferences[STORIES_KEY] ?: ""
        }.firstOrNull()

        if (storiesJson.isNullOrBlank()) return null

        val storiesType = object : TypeToken<List<ListStoryItem>>() {}.type
        return Gson().fromJson(storiesJson, storiesType)
    }

    suspend fun saveStories(stories: List<ListStoryItem>) {
        val storiesJson = Gson().toJson(stories)
        dataStore.edit { preferences ->
            preferences[STORIES_KEY] = storiesJson
        }
    }

    
    suspend fun saveSession(user: UserModel) {
        dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = user.token
            preferences[EMAIL_KEY] = user.email
            preferences[IS_LOGIN_KEY] = true
        }
    }

    fun getSession(): Flow<UserModel> {
        return dataStore.data.map { preferences ->
            UserModel(
                preferences[TOKEN_KEY] ?: "",
                preferences[EMAIL_KEY] ?: "",
                "",
                preferences[USERNAME_KEY] ?: "",
                preferences[IS_LOGIN_KEY] ?: false,
            )
        }
    }


    fun getToken(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[TOKEN_KEY] ?: "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLVVySXh0NlhnbmRGVHhJdE0iLCJpYXQiOjE2OTUzNDQ1NjV9.wJyW4l6fAot2K7zMc6KRn9EYO7DckhIj3lT7Hx2zz6s"
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        private val EMAIL_KEY = stringPreferencesKey("email")
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val USERNAME_KEY = stringPreferencesKey("username")
        private val IS_LOGIN_KEY = booleanPreferencesKey("isLogin")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}