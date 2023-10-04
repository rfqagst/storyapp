package com.example.storyintermediate.view.signup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storyintermediate.api.response.ErrorResponse
import com.example.storyintermediate.api.response.RegisterResponse
import com.example.storyintermediate.data.repo.UserRepo
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class SignupViewModel(private val repo: UserRepo) : ViewModel() {

    private val _registrationResponse = MutableLiveData<RegisterResponse>()
    val errorMessage = MutableLiveData<String?>()
    val registerStatus: MutableLiveData<Boolean> = MutableLiveData()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun register(username: String, email: String, password: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val registerResponse = repo.register(username, email, password)
                _isLoading.value = false
                Log.d("SignupActivity", "Berhasil mendaftar: ${registerResponse.message}")
                registerStatus.postValue(true)
            } catch (e: HttpException) {
                _isLoading.value = false
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                errorMessage.postValue(errorBody.message)
                registerStatus.postValue(false)
            } catch (e: Exception) {
                _isLoading.value = false
                Log.e("SignupActivity", "Kesalahan: ${e.message}")
                errorMessage.postValue("Terjadi kesalahan saat pendaftaran")
                registerStatus.postValue(false)
            }
        }

    }
}