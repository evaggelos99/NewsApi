package com.example.coursework

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import android.util.Log
import android.widget.RemoteViews
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.example.coursework.ArticleFeedWidget.Companion.ACTION_TOAST
import com.example.coursework.ui.account.ui.TopicsFragment


class ArticleFeedWidget : AppWidgetProvider() {
    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }

    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (ACTION_TOAST.equals(intent?.action)) {
            val newIntent = Intent(Intent.ACTION_VIEW)
            val url = intent?.getStringExtra(EXTRA_ITEM_POSITION)
            newIntent.data = Uri.parse(url)
            newIntent.flags = FLAG_ACTIVITY_NEW_TASK
            context?.startActivity(newIntent)
        }
        super.onReceive(context, intent)

    }

    override fun onEnabled(context: Context) {
        super.onEnabled(context)
    }


    override fun onDisabled(context: Context) {
       super.onDisabled(context)
    }

    companion object {
        val ACTION_TOAST = "onClick"
        val EXTRA_ITEM_POSITION = "newItemPosition"
    }

}


fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
    val widgetText = context.getString(R.string.appwidget_text)

    val intentProvider = Intent(context,WidgetService::class.java)
    val clickIntent1 = Intent(context, ArticleFeedWidget::class.java)
    clickIntent1.setAction(ACTION_TOAST)
    val pendingIntent1 = PendingIntent.getBroadcast(context, 0,clickIntent1,0)

    intentProvider.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
    intentProvider.data = Uri.parse(intentProvider.toUri(Intent.URI_INTENT_SCHEME))
    val views = RemoteViews(context.packageName, R.layout.article_feed_widget)
    views.setRemoteAdapter(R.id.list_view_widget, intentProvider)
    //views.setTextViewText(R.id.appwidget_text, widgetText)
    // Instruct the widget manager to update the widget
    val intent = Intent(context, ArticleFeedWidget::class.java)

    val clickIntent = Intent(context, WidgetService::class.java)
    clickIntent.setAction(ACTION_TOAST)
    val pendingIntent = PendingIntent.getBroadcast(context,0,clickIntent,0)
    views.setPendingIntentTemplate(R.id.list_view_widget, pendingIntent)
    views.setPendingIntentTemplate(R.id.list_view_widget,pendingIntent1)
    intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
    intent.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))
    //views.setRemoteAdapter(R.id.list_view_widget, intent)
    appWidgetManager.updateAppWidget(appWidgetId, views)

}