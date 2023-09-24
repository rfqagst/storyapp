package com.example.storyintermediate.di

import android.content.Context
import com.example.storyintermediate.api.retrofit.ApiConfig
import com.example.storyintermediate.data.StoryRepo
import com.example.storyintermediate.data.UserRepo
import com.example.storyintermediate.data.pref.UserPreference
import com.example.storyintermediate.data.pref.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideUserRepository(context: Context): UserRepo {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        return UserRepo.getInstance(apiService, pref)
    }

    fun provideStoryRepository(context: Context): StoryRepo {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        return StoryRepo.getInstance(apiService, pref)
    }
}