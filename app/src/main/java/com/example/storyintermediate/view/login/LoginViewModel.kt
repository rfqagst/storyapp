package com.example.storyintermediate.view.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storyintermediate.api.response.ErrorResponse
import com.example.storyintermediate.api.response.LoginResponse
import com.example.storyintermediate.data.UserRepo
import com.example.storyintermediate.data.pref.UserModel
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel(private val repo: UserRepo) : ViewModel() {
    private val _result: MutableLiveData<LoginResponse> = MutableLiveData()
    val result: LiveData<LoginResponse> get() = _result

    val loginStatus: MutableLiveData<Boolean> = MutableLiveData()
    val errorMessage = MutableLiveData<String?>()


    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = repo.login(email, password)
                if (response.error == false) {
                    val token = response.loginResult?.token
                    saveSession(UserModel(token ?: "", email, password, "", true))
                    loginStatus.postValue(true)
                } else {
                    Log.d("LOGIN_FAILED", "LOGIN FAILED")
                    loginStatus.postValue(false)
                }
            } catch (e: HttpException) {
                // Try to parse the error message from the server
                val jsonInString = e.response()?.errorBody()?.string()
                try {
                    val errorResponse = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                    errorMessage.postValue(errorResponse.message)
                } catch (jsonException: JsonSyntaxException) {
                    errorMessage.postValue("An error occurred.")
                }
            } catch (e: Exception) {
                val errorMessageText = "An error occurred."
                Log.e("LoginViewModel", errorMessageText, e)
                errorMessage.postValue(errorMessageText)
            }
        }
    }


    private fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repo.saveSession(user)
        }
    }

}