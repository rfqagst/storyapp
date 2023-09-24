package com.example.storyintermediate.view.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.example.storyintermediate.factory.StoryModelFactory
import com.example.storyintermediate.databinding.ActivityDetailBinding

import androidx.lifecycle.Observer

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
        Log.d("DetailActivity","Using id $id")

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

    }

    companion object {
        const val EXTRA_ID = "extra_user"
    }
}