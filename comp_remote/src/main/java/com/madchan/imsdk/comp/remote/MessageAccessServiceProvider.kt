package com.madchan.imsdk.comp.remote

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.IBinder
import android.text.TextUtils
import android.util.Log
import com.madchan.imsdk.comp.base.util.ProcessUtil
import com.madchan.imsdk.comp.remote.bean.Envelope
import com.madchan.imsdk.comp.remote.listener.MessageReceiver
import com.madchan.imsdk.comp.remote.service.MessageAccessService
import com.madchan.imsdk.comp.remote.util.EnvelopeHelper
import com.madchan.imsdk.comp.remote.work.WebSocketServerDiscoverWork
import com.madchan.imsdk.lib.objects.bean.vo.MessageVo

object MessageAccessServiceProvider {

    private var TAG = MessageAccessServiceProvider.javaClass.simpleName

    /** 全局Application的上下文*/
    private lateinit var appContext: Context

    /**
     * ## 消息接入服务返回的Binder对象
     * 定义了发送消息和注册/反注册消息接收器的方法
     */
    private var messageCarrier: MessageCarrier? = null

    /** 是否已绑定服务 */
    private var isBound = false

    /**
     * ## 绑定消息接入服务
     * 同时调用bindService和startService, 可以使unbind后Service仍保持运行
     * @param context   上下文
     */
    @Synchronized
    fun setupService(context: Context? = null) {
        if (!::appContext.isInitialized) {
            appContext = context!!.applicationContext
        }

        val intent = Intent(appContext, MessageAccessService::class.java)

        // 记录绑定服务的结果，避免解绑服务时出错
        if (!isBound) {
            isBound = appContext.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        }

        startService(intent)
    }

    /**
     * 启动消息接入服务
     * @param intent    意图
     * @param action    操作
     */
    private fun startService(
        intent: Intent = Intent(appContext, MessageAccessService::class.java),
        action: String? = null
    ) {
        if (!TextUtils.isEmpty(action)) intent.action = action
        // Android8.0不再允许后台service直接通过startService方式去启动,将引发IllegalStateException
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
            && !ProcessUtil.isForeground(appContext)
        ) {
            appContext.startForegroundService(intent)
        } else {
            appContext.startService(intent)
        }
    }

    /** 监听与服务连接状态的接口 */
    private val serviceConnection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            // 取得MessageCarrier.aidl对应的操作接口
            messageCarrier = MessageCarrier.Stub.asInterface(service)
            // 把接收消息的回调接口注册到服务端
            messageCarrier?.registerReceiveListener(messageReceiver)
            //设置Binder死亡监听
            messageCarrier?.asBinder()?.linkToDeath(deathRecipient, 0)

            // 绑定服务成功后再初始化WebSocket连接
            startService(action = MessageAccessService.ACTION_INITIALIZE_CONNECTION)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
        }

    }


    /** 接收远程服务器消息的接口 */
    private val messageReceiver = object : MessageReceiver.Stub() {
        override fun onMessageReceived(envelope: Envelope) {
            Log.d(TAG, "收取消息: ${envelope?.messageVo?.content}")
        }
    }

    /** 监听Binder死亡的代理对象 */
    private val deathRecipient = object : IBinder.DeathRecipient {
        override fun binderDied() {
            // Binder死亡的情况下，解除该代理
            messageCarrier?.asBinder()?.unlinkToDeath(this, 0)
            messageCarrier = null

            // 重连服务
            setupService()
        }
    }

    /**
     * 停止消息接入服务
     */
    fun stopService() {
        unbindService()
        appContext.stopService(Intent(appContext, MessageAccessService::class.java))
    }

    /**
     * 解绑消息接入服务
     */
    @Synchronized
    fun unbindService() {
        if (!isBound) return // 必须判断服务是否已解除绑定，否则会报java.lang.IllegalArgumentException: Service not registered

        // 解除消息监听接口
        if (messageCarrier?.asBinder()?.isBinderAlive == true) {
            messageCarrier?.unregisterReceiveListener(messageReceiver)
            messageCarrier = null
        }

        appContext.unbindService(serviceConnection)

        isBound = false
    }

    fun getWebSocketServer() {
        WebSocketServerDiscoverWork.enqueueAndObserve()
    }

    fun sendMessage(messageVo: MessageVo) {
        val envelope = Envelope()
        envelope.messageVo = messageVo
        messageCarrier?.sendMessage(envelope)
    }

}