package com.madchan.imsdk.demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.madchan.imsdk.comp.remote.MessageAccessServiceProvider
import com.madchan.imsdk.comp.ui.rongcloud.util.RouteUtils
import com.madchan.imsdk.demo.impl.ConversationActivityImpl

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MessageAccessServiceProvider.getWebSocketServer()

        val intent = Intent(this, ConversationActivityImpl::class.java);
        intent.putExtra(RouteUtils.TARGET_ID, "002")
        startActivity(intent)
    }
}