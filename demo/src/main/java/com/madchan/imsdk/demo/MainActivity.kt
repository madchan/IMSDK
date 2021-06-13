package com.madchan.imsdk.demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.madchan.imsdk.comp.remote.MessageAccessServiceProvider
import com.madchan.imsdk.comp.ui.activity.RongConversationActivity
import com.madchan.imsdk.sdk.IMMessageCreator
import com.madchan.imsdk.sdk.IMMessageSender
import com.madchan.imsdk.sdk.IMSDKClient

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MessageAccessServiceProvider.getWebSocketServer()

        startActivity(Intent(this, RongConversationActivity::class.java))
    }
}