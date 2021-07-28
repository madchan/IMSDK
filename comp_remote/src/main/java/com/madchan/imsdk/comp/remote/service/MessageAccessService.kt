package com.madchan.imsdk.comp.remote.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.RemoteCallbackList
import android.util.Log
import com.madchan.imsdk.comp.remote.MessageCarrier
import com.madchan.imsdk.comp.remote.bean.Envelope
import com.madchan.imsdk.comp.remote.constant.ConnectionStateMachine
import com.madchan.imsdk.comp.remote.exception.IllegalConnectionException
import com.madchan.imsdk.comp.remote.listener.MessageReceiver
import com.madchan.imsdk.comp.remote.observer.ConnectionStateObserver
import com.madchan.imsdk.comp.remote.pipe.MessageRetrievalThread
import com.madchan.imsdk.comp.remote.util.EnvelopeHelper
import com.madchan.imsdk.comp.remote.websocket.WebSocketConnection
import okio.ByteString

/**
 * 消息接入服务
 * <p>
 * 为了保证稳定性，需要和UI进程分离，分离后即使UI进程退出、Crash或者出现内存消耗过高等情况，仍不影响消息接入服务。
 * 此服务负责收发消息，并和远程服务器保持长连接。UI进程可通过此服务发送消息到远程服务器，此服务收到远程服务器消息通知UI进程；
 */
class MessageAccessService: Service() {

    /** WebSocket连接 */
    private val webSocketConnection = WebSocketConnection()
    /** 消息检索线程 */
    private var messageRetrievalThread: MessageRetrievalThread? = null

    /** 专门用来管理多进程回调接口 */
    private val remoteCallbackList = RemoteCallbackList<MessageReceiver>()

    /** 根据MessageCarrier.aidl文件自动生成的Binder对象，需要返回给客户端 */
    private val messageCarrier: IBinder = object : MessageCarrier.Stub() {
        override fun sendMessage(envelope: Envelope) {
            Log.d(TAG, "Send a message: " + envelope.messageVO?.content)

            val messageDTO = EnvelopeHelper.stuff(envelope)
            messageDTO?.let { webSocketConnection.send(ByteString.of(*it.toByteArray())) }
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
            ACTION_INITIALIZE_CONNECTION -> initWebSocketConnection()
        }

        // 如果系统在 onStartCommand() 返回后终止服务，则其会重建服务并调用 onStartCommand()，
        // 但不会重新传递最后一个 Intent。
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return messageCarrier
    }

    private fun initWebSocketConnection() {
        webSocketConnection.connect()
        webSocketConnection.addConnectionStateObserver(object : ConnectionStateObserver {

            override fun onChange(stateMachine: ConnectionStateMachine) {
                when (stateMachine) {
                    ConnectionStateMachine.CONNECTED -> {
                        if (messageRetrievalThread == null) {
                            messageRetrievalThread = initMessageRetrievalThread()
                            messageRetrievalThread?.start()
                        }
                    }
                }
            }

            override fun onFailure(exception: IllegalConnectionException) {
            }

            override fun onActive() {
            }

        })
    }

    fun initMessageRetrievalThread() = MessageRetrievalThread(
        webSocketConnection.incomingMessagePipe,
        object : MessageRetrievalThread.MessageRetrievalCallback {
            override fun onReadMessage(envelope: Envelope?) {
                envelope?.let {
                    val number = remoteCallbackList.beginBroadcast()
                    for (i in 0 until number) {
                        remoteCallbackList.getBroadcastItem(i).onMessageReceived(envelope)
                    }
                    remoteCallbackList.finishBroadcast()
                }
            }
        })

    companion object {
        var TAG = MessageAccessService::class.java.simpleName

        /** 操作类型-初始化连接 */
        const val ACTION_INITIALIZE_CONNECTION = "INITIALIZE_CONNECTION"
    }

}