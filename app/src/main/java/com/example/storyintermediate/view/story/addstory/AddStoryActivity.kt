package com.example.storyintermediate.view.story.addstory

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.storyintermediate.R
import com.example.storyintermediate.ResultState
import com.example.storyintermediate.databinding.ActivityAddStoryBinding
import com.example.storyintermediate.factory.StoryModelFactory
import com.example.storyintermediate.utils.getImageUri
import com.example.storyintermediate.utils.reduceFileImage
import com.example.storyintermediate.utils.uriToFile
import com.example.storyintermediate.view.story.StoryActivity

class AddStoryActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddStoryBinding
    private var currentImageUri: Uri? = null

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
            val intent = Intent(this, StoryActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
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
            Log.d("Image File", "showImage: ${imageFile.path}")
            val description = binding.descriptionEditText.text.toString()
            addStoryViewModel.postStory(imageFile, description).observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is ResultState.Loading -> {
                            Log.d("Image Loading", " Lagi Loading")
                        }

                        is ResultState.Success -> {
                            Log.d("Image Success", "showImage: ${result.data.message.toString()}")
                            showToast(result.data.message.toString())
//                            showLoading(false)
                        }

                        is ResultState.Error -> {
                            showToast(result.error)
                            Log.d("Image Error", "showImage: ${result.error}")
//                            showLoading(false)
                        }
                    }
                }
            }
        } ?: showToast(getString(R.string.empty_image_warning))
    }

//    private fun showLoading(isLoading: Boolean) {
//        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
//    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}