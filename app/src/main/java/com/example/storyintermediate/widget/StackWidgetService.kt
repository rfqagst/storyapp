package com.example.storyintermediate.widget

import android.content.Intent
import android.widget.RemoteViewsService
import com.example.storyintermediate.di.Injection


class StackWidgetService : RemoteViewsService() {
    private val storyRepo by lazy { Injection.provideStoryRepository(this.applicationContext) }
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory =
        StackRemoteViewsFactory(this.applicationContext, storyRepo)

}