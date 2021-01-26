package com.madchan.imsdk.comp.remote.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.RemoteCallbackList
import android.util.Log
import com.madchan.imsdk.comp.remote.MessageCarrier
import com.madchan.imsdk.comp.remote.bean.Envelope
import com.madchan.imsdk.comp.remote.listener.MessageReceiver
import com.madchan.imsdk.comp.remote.util.extract
import com.madchan.imsdk.comp.remote.util.stuff
import com.madchan.imsdk.lib.objects.bean.vo.MessageVo
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MessageAccessService: Service() {

    /** 专门用来管理多进程回调接口 */
    private val remoteCallbackList = RemoteCallbackList<MessageReceiver>()

    /** 根据MessageCarrier.aidl文件自动生成的Binder对象，需要返回给客户端 */
    private val messageCarrier: IBinder = object : MessageCarrier.Stub() {
        override fun sendMessage(envelope: Envelope) {
            Log.d(TAG, "发送消息: " + stuff(envelope)?.content)
        }

        override fun registerReceiveListener(messageReceiver: MessageReceiver?) {
            remoteCallbackList.register(messageReceiver)
        }

        override fun unregisterReceiveListener(messageReceiver: MessageReceiver?) {
            remoteCallbackList.unregister(messageReceiver)
        }

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        when(intent?.action) {
            // 初始化连接
            ACTION_INITIALIZE_CONNECTION -> MockServer().listener()
        }

        // 如果系统在 onStartCommand() 返回后终止服务，则其会重建服务并调用 onStartCommand()，
        // 但不会重新传递最后一个 Intent。
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return messageCarrier
    }

    companion object {
        var TAG = MessageAccessService::class.java.simpleName
        /** 操作类型-初始化连接 */
        const val ACTION_INITIALIZE_CONNECTION = "INITIALIZE_CONNECTION"
    }

    inner class MockServer {
        fun listener() {
            GlobalScope.launch {
                while (true) {
                    delay(5000)

                    val listenerCount = remoteCallbackList.beginBroadcast()
                    for (i in 0 until listenerCount) {
                        val messageReceiver = remoteCallbackList.getBroadcastItem(i)
                        messageReceiver?.onMessageReceived(extract(MessageVo("来自服务端的消息")))
                    }
                    remoteCallbackList.finishBroadcast()
                }
            }
        }
    }
}