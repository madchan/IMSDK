package com.madchan.imsdk.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.madchan.imsdk.comp.remote.MessageAccessServiceProvider
import com.madchan.imsdk.sdk.IMMessageCreator
import com.madchan.imsdk.sdk.IMMessageSender
import com.madchan.imsdk.sdk.IMSDKClient

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MessageAccessServiceProvider.getWebSocketServer()

        val editText = findViewById<EditText>(R.id.editText)
        findViewById<Button>(R.id.button).setOnClickListener {
            IMMessageSender.sendMessage(IMMessageCreator.createTextMessage("001", "002", editText.text.toString()))
        }
    }
}