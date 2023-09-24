package com.example.storyintermediate.data

import android.util.Log
import androidx.lifecycle.liveData
import com.example.storyintermediate.ResultState
import com.example.storyintermediate.api.response.AddStoryResponse
import com.example.storyintermediate.api.response.DetailResponse
import com.example.storyintermediate.api.response.StoryResponse
import com.example.storyintermediate.api.retrofit.ApiService
import com.example.storyintermediate.data.pref.UserPreference
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File


class StoryRepo(private val apiService: ApiService, private val pref: UserPreference) {
    suspend fun getStories(): StoryResponse {
        val response = apiService.getStories()
        pref.saveStories(response.listStory)
        return response
    }

    suspend fun getStoryDetail(id: String): DetailResponse {
        return apiService.getDetailStory(id)
    }

    fun postStory(imageFile: File, description: String) = liveData {
        emit(ResultState.Loading)
        val requestBody = description.toRequestBody("text/plain".toMediaType())
        val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "photo",
            imageFile.name,
            requestImageFile
        )
        try {
            val successResponse = apiService.postStory(multipartBody, requestBody)
            emit(ResultState.Success(successResponse))
            Log.d("StoryRepo", "Berhasil Upload")
        } catch (e: HttpException) {

            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, AddStoryResponse::class.java)
            emit(ResultState.Error(errorResponse.message.toString()))
            Log.d("StoryRepo", "Gagal Upload")
        }
    }

    companion object {
        @Volatile
        private var instance: StoryRepo? = null
        fun getInstance(apiService: ApiService, pref: UserPreference): StoryRepo =
            instance ?: synchronized(this) {
                instance ?: StoryRepo(apiService, pref)
            }.also { instance = it }
    }
}


