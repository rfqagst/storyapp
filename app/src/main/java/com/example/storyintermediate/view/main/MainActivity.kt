package com.example.storyintermediate.view.main

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.storyintermediate.R
import com.example.storyintermediate.databinding.ActivityMainBinding
import com.example.storyintermediate.factory.UserModelFactory
import com.example.storyintermediate.view.login.LoginActivity
import com.example.storyintermediate.view.maps.MapsActivity
import com.example.storyintermediate.view.signup.SignupActivity
import com.example.storyintermediate.view.story.StoryActivity

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel

    private val userViewModel by viewModels<MainViewModel> {
        UserModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userViewModel.getSession().observe(this) { user ->
            if (user.isLogin) {
                val intent = Intent(this, StoryActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }
        }
        setupAction()
    }

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.signupButton.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_bahasa -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }
}