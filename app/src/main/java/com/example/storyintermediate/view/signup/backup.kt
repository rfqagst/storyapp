//package com.example.storyintermediate.view.signup
//
//import android.util.Log
//import android.widget.Toast
//import com.example.storyintermediate.api.retrofit.ApiConfig
//
//class backup {
//    lifecycleScope.launch {
//        try {
//            val apiService = ApiConfig.getApiService()
//            val registerResponse = apiService.register(username, email, password)
//
//            if (registerResponse.error == false) {
//                Log.d("SignupActivity", "Berhasil mendaftar: ${registerResponse.message}")
//                Toast.makeText(
//                    this@SignupActivity,
//                    "Berhasil mendaftar: ${registerResponse.message}",
//                    Toast.LENGTH_SHORT
//                ).show()
//            } else {
//                Log.e("SignupActivity", "Gagal mendaftar atau terjadi kesalahan")
//                Toast.makeText(
//                    this@SignupActivity,
//                    "Gagal mendaftar atau terjadi kesalahan",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//        } catch (e: Exception) {
//            Log.e("SignupActivity", "Kesalahan: ${e.message}")
//            Toast.makeText(
//                this@SignupActivity,
//                "Terjadi kesalahan: ${e.message}",
//                Toast.LENGTH_SHORT
//            ).show()
//        }
//    }
//}