package com.example.storyintermediate.view.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storyintermediate.api.response.ErrorResponse
import com.example.storyintermediate.api.response.LoginResponse
import com.example.storyintermediate.data.repo.UserRepo
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

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = repo.login(email, password)
                _isLoading.value = false
                if (response.error == false) {
                    val token = response.loginResult?.token
                    saveSession(UserModel(token ?: "", email, password, "", true))
                    loginStatus.postValue(true)
                }
                else {
                    loginStatus.postValue(false)
                }
            } catch (e: HttpException) {
                _isLoading.value = false
                val jsonInString = e.response()?.errorBody()?.string()
                try {
                    val errorResponse = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                    errorMessage.postValue(errorResponse.message)
                } catch (jsonException: JsonSyntaxException) {
                    errorMessage.postValue("An error occurred.")
                }
            } catch (e: Exception) {
                _isLoading.value = false
                val errorMessageText = "An error occurred."
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