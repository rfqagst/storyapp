package com.example.storyintermediate.view.story

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.storyintermediate.R
import com.example.storyintermediate.data.pref.UserPreference
import com.example.storyintermediate.data.pref.dataStore
import com.example.storyintermediate.databinding.ActivityStoryBinding
import com.example.storyintermediate.factory.StoryModelFactory
import com.example.storyintermediate.view.main.MainActivity
import com.example.storyintermediate.view.maps.MapsActivity
import com.example.storyintermediate.view.story.addstory.AddStoryActivity
import kotlinx.coroutines.launch

class StoryActivity : AppCompatActivity() {
    private lateinit var adapter: StoryListAdapter
    private lateinit var binding: ActivityStoryBinding

    private val storyViewModel by viewModels<StoryViewModel> {
        StoryModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.addFab.setOnClickListener {
            startActivity(Intent(this, AddStoryActivity::class.java))
        }
        setupRecyclerView()
    }
    private fun setupRecyclerView() {
        adapter = StoryListAdapter()
        binding.rvStory.layoutManager = LinearLayoutManager(this)
        binding.rvStory.adapter = adapter

        storyViewModel.storyPagingData.observe(this) {
            adapter.submitData(lifecycle, it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.story_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val userPreference = UserPreference.getInstance(dataStore)
        when (item.itemId) {
            R.id.menu_logout -> {
                lifecycleScope.launch {
                    userPreference.logout()
                    startActivity(Intent(this@StoryActivity, MainActivity::class.java))
                    finish()
                }
                return true
            }

            R.id.menu_bahasa -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                return true
            }

            R.id.menu_map -> {
                startActivity(Intent(this, MapsActivity::class.java))
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }


    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        finishAffinity()
    }

}
