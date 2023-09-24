package com.example.storyintermediate.data.pref

data class UserModel(
    val token: String,
    val email: String,
    val password: String,
    val username: String,
    val isLogin: Boolean = false,
)
