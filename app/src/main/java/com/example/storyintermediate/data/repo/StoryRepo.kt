package com.example.storyintermediate.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.storyintermediate.ResultState
import com.example.storyintermediate.api.response.DetailResponse
import com.example.storyintermediate.api.response.ListStoryItem
import com.example.storyintermediate.api.response.StoryResponse
import com.example.storyintermediate.api.retrofit.ApiService
import com.example.storyintermediate.data.paging.StoryPagingSource
import com.example.storyintermediate.data.paging.StoryRemoteMediator
import com.example.storyintermediate.data.pref.UserPreference
import com.example.storyintermediate.database.StoryDatabase
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File


class StoryRepo(
    private val apiService: ApiService,
    private val storyDatabase: StoryDatabase,
    private val pref: UserPreference
) {
    fun getStoriesMediator(): LiveData<PagingData<ListStoryItem>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = StoryRemoteMediator(storyDatabase, apiService),
            pagingSourceFactory = {
                StoryPagingSource(apiService)
                storyDatabase.storyDao().getAllStory()
            }
        ).liveData
    }

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
            val successResponse =
                apiService.postStory(multipartBody, requestBody)
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, StoryResponse::class.java)
            emit(ResultState.Error(errorResponse.message))
        }
    }


    fun postStoryWithLocation(imageFile: File, description: String, lat: Double, lon: Double) =
        liveData {
            emit(ResultState.Loading)
            val requestBody = description.toRequestBody("text/plain".toMediaType())
            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())

            val multipartBody = MultipartBody.Part.createFormData(
                "photo",
                imageFile.name,
                requestImageFile
            )
            try {
                val successResponse =
                    apiService.postStoryWithLocation(multipartBody, requestBody, lat, lon)
                emit(ResultState.Success(successResponse))
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, StoryResponse::class.java)
                emit(ResultState.Error(errorResponse.message))
            }
        }

    companion object {
        @Volatile
        private var instance: StoryRepo? = null
        fun getInstance(
            apiService: ApiService, storyDatabase: StoryDatabase,
            pref: UserPreference
        ): StoryRepo =
            instance ?: synchronized(this) {
                instance ?: StoryRepo(apiService, storyDatabase, pref)
            }.also { instance = it }
    }
}


