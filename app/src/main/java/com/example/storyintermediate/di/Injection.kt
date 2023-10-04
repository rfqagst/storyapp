package com.example.storyintermediate.di

import android.content.Context
import com.example.storyintermediate.api.retrofit.ApiConfig
import com.example.storyintermediate.data.pref.UserPreference
import com.example.storyintermediate.data.pref.dataStore
import com.example.storyintermediate.data.repo.MapsRepo
import com.example.storyintermediate.data.repo.StoryRepo
import com.example.storyintermediate.data.repo.UserRepo
import com.example.storyintermediate.database.StoryDatabase
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
        val storyDatabase = StoryDatabase.getDatabase(context)

        return StoryRepo.getInstance(apiService, storyDatabase, pref)
    }


    fun provideMapsRepository(context: Context): MapsRepo {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        return MapsRepo.getInstance(apiService)
    }
}