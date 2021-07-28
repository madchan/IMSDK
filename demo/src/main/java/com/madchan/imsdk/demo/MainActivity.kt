package com.madchan.imsdk.demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.madchan.imsdk.comp.remote.MessageAccessServiceProvider
import com.madchan.imsdk.comp.ui.rongcloud.util.RouteUtils
import com.madchan.imsdk.demo.impl.ConversationActivityImpl
import com.madchan.imsdk.sdk.IMClient

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        IMClient.connect("mock_token")

        val intent = Intent(this, ConversationActivityImpl::class.java);
        intent.putExtra(RouteUtils.TARGET_ID, "002")
        startActivity(intent)

        Log.d("TAG", )
    }
}