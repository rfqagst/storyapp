package com.example.storyintermediate.view.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.storyintermediate.R
import com.example.storyintermediate.databinding.ActivityDetailBinding
import com.example.storyintermediate.factory.StoryModelFactory
import com.example.storyintermediate.view.story.StoryActivity

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val detailViewModel by viewModels<DetailViewModel> {
        StoryModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val id = intent?.getStringExtra(EXTRA_ID) ?: ""
        detailViewModel.getStoryDetail(id)
        detailViewModel.detailStory.observe(this, Observer
        { _ ->
            binding.apply {
                tvName.text = detailViewModel.detailStory.value?.story?.name
                tvDescription.text = detailViewModel.detailStory.value?.story?.description
                Glide.with(this@DetailActivity)
                    .load(detailViewModel.detailStory.value?.story?.photoUrl)
                    .into(binding.imgBanner)
            }
        })

        with(supportActionBar) {
            this?.setDisplayShowCustomEnabled(true)
            this?.setDisplayShowTitleEnabled(false)
            this?.setDisplayShowHomeEnabled(false)
            this?.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    companion object {
        const val EXTRA_ID = "extra_user"
    }
}