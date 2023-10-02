package com.example.storyintermediate.data.repo

import com.example.storyintermediate.api.response.LoginResponse
import com.example.storyintermediate.api.response.RegisterResponse
import com.example.storyintermediate.api.retrofit.ApiService
import com.example.storyintermediate.data.pref.UserModel
import com.example.storyintermediate.data.pref.UserPreference
import kotlinx.coroutines.flow.Flow

class UserRepo(private val apiService: ApiService, val userPreference: UserPreference) {

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    suspend fun login(email: String, password: String): LoginResponse {
        return apiService.login(email, password)
    }

    suspend fun register(username: String, email: String, password: String): RegisterResponse {
        return apiService.register(username, email, password)
    }

    companion object {
        @Volatile
        private var instance: UserRepo? = null
        fun getInstance(apiService: ApiService, userPreference: UserPreference): UserRepo =
            instance ?: synchronized(this) {
                instance ?: UserRepo(apiService, userPreference)
            }.also { instance = it }
    }
}