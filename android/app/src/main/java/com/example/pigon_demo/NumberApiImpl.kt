package com.example.pigeon_demo
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.EventChannel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NumberApiImpl {
    private var number = 0
    private val EVENT_CHANNEL = "com.example.pigeon_demo/number_events"

    fun startSendingNumbersEveryTwoSeconds(flutterEngine: FlutterEngine) {
        val eventChannel = EventChannel(flutterEngine.dartExecutor.binaryMessenger, EVENT_CHANNEL)
        eventChannel.setStreamHandler(object : EventChannel.StreamHandler {
            override fun onListen(arguments: Any?, events: EventChannel.EventSink?) {
                val eventSink = events ?: return
                CoroutineScope(Dispatchers.IO).launch {
                    while (true) {
                        delay(2000) // Suspend instead of Thread.sleep
                        number++
                        withContext(Dispatchers.Main) {
                            eventSink.success(number) // Send data on main thread
                        }
                    }
                }

            }

            override fun onCancel(arguments: Any?) {
                // Clean up resources if needed
            }
        })
    }
}
