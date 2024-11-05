package com.example.pigon_demo

import com.example.pigon_demo.Pigeon.Mymessage

class MessageApiImpl : Pigeon.MessageApi {
    private val messages = listOf(
        Mymessage.Builder().setTitle("title1").setBody("body1").setEmail("jaiho@gmail.com").build(),
        Mymessage.Builder().setTitle("title2").setBody("body2").setEmail("jaiho1@gmail.com").build(),
        Mymessage.Builder().setTitle("title3").setBody("body3").setEmail("jaiho2@gmail.com").build()
    )

   override fun getMessage(fromEmail: String): MutableList<Mymessage> {
        return messages.filter { it.email == fromEmail }.toMutableList()
    }


}
