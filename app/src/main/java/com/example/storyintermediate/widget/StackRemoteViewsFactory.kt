package com.example.storyintermediate.widget

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.bumptech.glide.Glide
import com.example.storyintermediate.R
import com.example.storyintermediate.api.response.ListStoryItem
import com.example.storyintermediate.data.repo.StoryRepo
import com.example.storyintermediate.view.detail.DetailActivity.Companion.EXTRA_ID
import com.example.storyintermediate.widget.StoryWidget.Companion.EXTRA_ITEM
import kotlinx.coroutines.runBlocking


internal class StackRemoteViewsFactory(
    private val context: Context,
    private val storyRepo: StoryRepo,
) : RemoteViewsService.RemoteViewsFactory {

    private val storiesBitmap = arrayListOf<Bitmap>()
    private val stories = arrayListOf<ListStoryItem>()

    override fun onCreate() {}

    override fun onDataSetChanged(): Unit = runBlocking {
        try {
            val response = storyRepo.getStories()
            val storyList = response.listStory
            val bitmap = storyList.map {
                Glide.with(context)
                    .asBitmap()
                    .load(it.photoUrl)
                    .submit()
                    .get()
            }

            storiesBitmap.clear()
            stories.clear()
            storiesBitmap.addAll(bitmap)
            stories.addAll(storyList)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {}

    override fun getCount(): Int = stories.size


    override fun getViewAt(position: Int): RemoteViews {
        val remoteViews = RemoteViews(context.packageName, R.layout.widget_item)
        remoteViews.setImageViewBitmap(R.id.iv_story, storiesBitmap[position])
        val fillInIntent = Intent().apply {
            action = EXTRA_ITEM
            putExtra(EXTRA_ID, stories[position].id)
        }
        remoteViews.setOnClickFillInIntent(R.id.iv_story, fillInIntent)  // Note the change here

        return remoteViews
    }

    override fun getLoadingView(): RemoteViews? = null


    override fun getViewTypeCount(): Int = 1


    override fun getItemId(position: Int): Long = position.toLong()


    override fun hasStableIds(): Boolean = true


}
