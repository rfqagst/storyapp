package com.example.storyintermediate.api.retrofit

import com.example.storyintermediate.api.response.DetailResponse
import com.example.storyintermediate.api.response.LoginResponse
import com.example.storyintermediate.api.response.RegisterResponse
import com.example.storyintermediate.api.response.StoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ): RegisterResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String,
    ): LoginResponse

    @GET("stories")
    suspend fun getStories(): StoryResponse

    @GET("stories/{id}")
    suspend fun getDetailStory(@Path("id") id: String): DetailResponse

    @Multipart
    @POST("stories")
    suspend fun postStory(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): StoryResponse


    @Multipart
    @POST("stories")
    suspend fun postStoryWithLocation(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Part("lat") lat: Double,
        @Part("lon") lon: Double,
    ): StoryResponse

    @GET("stories")
    suspend fun getStoriesWithLocation(
        @Query("location") location: Int = 1,
    ): StoryResponse

    @GET("stories")
    suspend fun getStoriesPaging(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): StoryResponse
}