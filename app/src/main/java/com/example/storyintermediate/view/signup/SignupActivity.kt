package com.example.storyintermediate.view.signup

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.storyintermediate.R
import com.example.storyintermediate.databinding.ActivitySignupBinding
import com.example.storyintermediate.factory.UserModelFactory
import com.example.storyintermediate.view.login.LoginActivity

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding

    private val viewModel by viewModels<SignupViewModel> {
        UserModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signupButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val username = binding.nameEditText.text.toString()
            observeSignup()
            viewModel.register(username, email, password)
            setupView()
        }

        binding.loginButton.setOnClickListener {
            startActivity(
                Intent(this, LoginActivity::class.java)
            )
        }
    }

    private fun observeSignup() {
        viewModel.registerStatus.observe(this) { isSuccess ->
            if (isSuccess) {
                binding.warningText.text = getString(R.string.success_register)
                binding.warningText.setTextColor(ContextCompat.getColor(this, R.color.green))
            } else {
                Toast.makeText(this, "Register failed.", Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.errorMessage.observe(this) { errorMessage ->
            if (errorMessage != null) {
                binding.warningText.text = errorMessage
            }
        }
        viewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}