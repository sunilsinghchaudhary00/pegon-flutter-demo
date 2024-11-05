package com.example.pigon_demo
import io.flutter.embedding.android.FlutterActivity
import android.os.Bundle
import com.example.NotificationApiImpl
import com.example.pigeon_demo.NumberApiImpl

class MainActivity : FlutterActivity() {
    val numberApi = NumberApiImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         //METHOD HANDLING METHOD
        // Register the MessageApi implementation
        flutterEngine?.let {
            Pigeon.MessageApi.setUp(it.dartExecutor.binaryMessenger, MessageApiImpl())
        }

        flutterEngine?.dartExecutor?.let {
            Pigeon.NotificationApi.setUp(it.binaryMessenger, NotificationApiImpl(
                context = this
            ))
        }
        // EVENT HANDLING METHODS
        // Start sending numbers every two seconds
        numberApi.startSendingNumbersEveryTwoSeconds(flutterEngine!!)

    }
}
