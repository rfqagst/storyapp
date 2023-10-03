package com.example.storyintermediate.view.story.addstory

import android.content.Intent
import android.location.Location
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.storyintermediate.R
import com.example.storyintermediate.ResultState
import com.example.storyintermediate.api.response.AddStoryResponse
import com.example.storyintermediate.databinding.ActivityAddStoryBinding
import com.example.storyintermediate.factory.StoryModelFactory
import com.example.storyintermediate.utils.getImageUri
import com.example.storyintermediate.utils.reduceFileImage
import com.example.storyintermediate.utils.uriToFile
import com.example.storyintermediate.view.story.StoryActivity

class AddStoryActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddStoryBinding
    private var currentImageUri: Uri? = null
    private var currentLocation: Location? = null

    private val addStoryViewModel by viewModels<AddStoryViewModel> {
        StoryModelFactory.getInstance(this)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.galleryButton.setOnClickListener { startGallery() }
        binding.cameraButton.setOnClickListener { startCamera() }
        binding.submitStoryButton.setOnClickListener {
            uploadImage()
            Log.d("AddStory", "Submit di klik")
        }

        with(supportActionBar) {
            this?.setDisplayShowCustomEnabled(true)
            this?.setDisplayShowTitleEnabled(false)
            this?.setDisplayShowHomeEnabled(false)
            this?.setDisplayHomeAsUpEnabled(true)
        }

    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.imagePickerView.setImageURI(it)
        }
    }

    private fun startCamera() {
        currentImageUri = getImageUri(this)
        launcherIntentCamera.launch(currentImageUri)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        }
    }

    private fun uploadImage() {
        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, this).reduceFileImage()
            val description = binding.descriptionEditText.text.toString()
            if (binding.checkBoxLocation.isChecked) {
                addStoryViewModel.postStoryWithLocation(imageFile, description, currentLocation!!)
                    .observe(this) { result -> handleResultState(result) }
            } else {
                addStoryViewModel.postStory(imageFile, description).observe(this) { result ->
                    handleResultState(result)
                }
            }
        } ?: showToast(getString(R.string.empty_image_warning))
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intentFavorite = Intent(this, StoryActivity::class.java)
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun handleResultState(result: ResultState<AddStoryResponse>) {
        when (result) {
            is ResultState.Loading -> {
                showLoading(true)
            }

            is ResultState.Success -> {
                val response = result.data
                Log.d("Image Success", "showImage: ${response.message}")
                showLoading(false)
                val intent = Intent(this, StoryActivity::class.java)
                intent.flags =
                    Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }

            is ResultState.Error -> {
                val errorMessage = result.error
                Log.d("Image Error", "showImage: $errorMessage")
                binding.warningText.text = errorMessage
                binding.warningText.setTextColor(
                    ContextCompat.getColor(
                        this,
                        R.color.red
                    )
                )
                showLoading(false)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    companion object {
        const val EXTRA_LATITUDE = "extra_latitute"
        const val EXTRA_LONGITUDE = "extra_longitude"
    }
}
