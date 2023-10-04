package com.example.storyintermediate.view.story.addstory

import android.location.Location
import androidx.lifecycle.ViewModel
import com.example.storyintermediate.data.repo.StoryRepo
import com.google.android.gms.maps.model.LatLng
import java.io.File


class AddStoryViewModel(private val repo: StoryRepo) : ViewModel() {
    fun postStory(file: File, description: String) = repo.postStory(file, description)

    fun postStoryWithLocation(file: File, description: String, lat: Double, lon : Double) =

        repo.postStoryWithLocation(file, description, lat,lon)

}
