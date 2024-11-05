package com.example

import android.app.NotificationManager
import android.content.ContentResolver
import android.net.Uri
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import com.example.pigon_demo.Pigeon
import android.app.NotificationChannel;
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.media.AudioAttributes;
import android.content.Intent

class NotificationApiImpl(private val context: Context) : Pigeon.NotificationApi {
  override fun createNotificationChannel(data: Pigeon.NotificationData): Boolean {
        val mapData = hashMapOf(
            "id" to data.id,
            "name" to data.name,
            "description" to data.description
        )
        return startcreateNotificationChannel(mapData)
    }

    override fun openFile(filePath: String): Boolean {
        openFileImpl(filePath)
        return true
    }

    private fun openFileImpl(filePath: String) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(Uri.parse(filePath), "application/pdf") // Change type as per requirement
            addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY) // Optional: to avoid adding to the history stack
        }
        context.startActivity(intent) // Use the context passed to the class

    }

    private fun startcreateNotificationChannel(mapData: HashMap<String, String?>): Boolean {
        val completed: Boolean
        if (VERSION.SDK_INT >= VERSION_CODES.O) {
            // Create the NotificationChannel
            val id = mapData["id"]
            val name = mapData["name"]
            val descriptionText = mapData["description"]
            val sound = "app_sound"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel(id, name, importance)
            mChannel.description = descriptionText

            val soundUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"+ context.packageName + "/raw/app_sound")
            val att = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                .build()

            mChannel.setSound(soundUri, att)

            // Register the channel with the system
            val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
            completed = true
        } else {
            completed = false
        }
        return completed
    }
}
