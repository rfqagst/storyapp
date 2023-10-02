package com.example.storyintermediate.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.storyintermediate.data.repo.UserRepo
import com.example.storyintermediate.di.Injection
import com.example.storyintermediate.view.login.LoginViewModel
import com.example.storyintermediate.view.main.MainViewModel
import com.example.storyintermediate.view.signup.SignupViewModel

class UserModelFactory(private val repo: UserRepo) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repo) as T
            }

            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repo) as T
            }

            modelClass.isAssignableFrom(SignupViewModel::class.java) -> {
                SignupViewModel(repo) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): UserModelFactory {
            return INSTANCE ?: synchronized(UserModelFactory::class.java) {
                INSTANCE ?: UserModelFactory(
                    Injection.provideUserRepository(context)
                ).also { INSTANCE = it }
            }
        }
    }
}