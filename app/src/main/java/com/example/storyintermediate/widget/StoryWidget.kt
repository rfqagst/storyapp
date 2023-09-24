package com.example.storyintermediate.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.core.net.toUri
import com.example.storyintermediate.R
import com.example.storyintermediate.view.detail.DetailActivity
import com.example.storyintermediate.view.detail.DetailActivity.Companion.EXTRA_ID

/**
 * Implementation of App Widget functionality.
 */
class StoryWidget : AppWidgetProvider() {


    companion object {

        private const val TOAST_ACTION = "com.example.storyintermediate.TOAST_ACTION"
        const val EXTRA_ITEM = "com.example.storyintermediate.EXTRA_ITEM"

        private fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            val intent = Intent(context, StackWidgetService::class.java)
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = intent.toUri(Intent.URI_INTENT_SCHEME).toUri()

            val views = RemoteViews(context.packageName, R.layout.story_widget)
            views.setRemoteAdapter(R.id.stack_view, intent)
            views.setEmptyView(R.id.stack_view, R.id.empty_view)

            val toastIntent = Intent(context, StoryWidget::class.java)
            toastIntent.action = EXTRA_ITEM
            toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)

            val toastPendingIntent = PendingIntent.getBroadcast(
                context, 0, toastIntent,
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
                else 0
            )
            views.setPendingIntentTemplate(R.id.stack_view, toastPendingIntent)

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if (EXTRA_ITEM == intent.action) {
            val storyId = intent.getStringExtra(EXTRA_ID)
            val detailIntent = Intent(context, DetailActivity::class.java).apply {
                putExtra(EXTRA_ID, storyId)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(detailIntent)
        }
    }

}
