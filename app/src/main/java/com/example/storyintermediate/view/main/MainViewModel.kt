package com.example.storyintermediate.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.storyintermediate.data.UserRepo
import com.example.storyintermediate.data.pref.UserModel
import kotlinx.coroutines.launch

class MainViewModel(private val repo: UserRepo) : ViewModel() {
    fun getSession(): LiveData<UserModel> {
        return repo.getSession().asLiveData()
    }
    fun logout() {
        viewModelScope.launch {
            repo.logout()
        }
    }
}