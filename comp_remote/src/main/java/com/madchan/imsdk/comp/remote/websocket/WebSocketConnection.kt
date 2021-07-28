package com.madchan.imsdk.comp.remote.websocket

import android.util.Log
import com.madchan.imsdk.comp.remote.constant.HttpStatusCode
import com.madchan.imsdk.comp.remote.constant.RemoteDataStoreKey
import com.madchan.imsdk.comp.remote.constant.WebSocketStatusCode
import com.madchan.imsdk.comp.remote.observer.ConnectionStateMachineSubscriber
import com.madchan.imsdk.comp.remote.observer.ConnectionStateObserver
import com.litalk.supportlib.lib.base.util.DataStoreUtil
import com.madchan.imsdk.comp.base.SDKCache
import com.madchan.imsdk.comp.remote.exception.IllegalConnectionException
import com.madchan.imsdk.comp.remote.pipe.IncomingMessagePipe
import com.madchan.imsdk.comp.remote.service.MessageAccessService
import com.madchan.imsdk.comp.remote.util.EnvelopeHelper
import com.madchan.imsdk.lib.objects.bean.dto.MessageDTO
import kotlinx.coroutines.runBlocking
import okhttp3.*
import okio.ByteString
import java.util.concurrent.TimeUnit

/**
 * # WebSocket连接单例
 * 用于建立、断开连接，并监听连接的状态
 */
class WebSocketConnection : WebSocketListener() {

    var TAG: String = this.javaClass.simpleName

    /** WebSocket服务器地址 */
    private var serverUrl: String? = null

    /**
     * ## WebSocket客户端实例
     * 该实例非线程安全，如果因多线程同时访问创建了多个实例，将导致消息收发业务异常。
     * 需要将创建该实例的代码块设为临界区，对该临界区加以保护，只允许一个线程访问
     * */
    private var webSocketClient: WebSocket? = null

    /** WebSocket连接状态机订阅者 */
    private var stateMachineSubscriber = ConnectionStateMachineSubscriber()

    val incomingMessagePipe = IncomingMessagePipe()

    /**
     * ## 建立连接
     * 请注意：调用newWebSocket方法将创建新的WebSocket实例并立即返回，并启动异步过程以连接Socket。
     * 成功或失败后，将通知连接状态监听器。
     * 当不再使用该WebSocket时，必须显式关闭或取消(否则可能产生多个WebSocket连接)
     */
    @Synchronized
    fun connect() {
        if (!stateMachineSubscriber.isIdle()) return

        runBlocking {
            serverUrl = DataStoreUtil.readString(
                SDKCache.dependentContext,
                RemoteDataStoreKey.WEB_SOCKET_SERVER_URL
            )
        }

        if (serverUrl.isNullOrEmpty()) {
            stateMachineSubscriber.fail(
                IllegalConnectionException(
                    HttpStatusCode.HTTP_STATUS_CODE_ERROR_PARAMS,
                    "The server address cannot be empty"
                )
            )
            return
        }

        Log.i(
            TAG,
            "[Connection Status]Try to establish a connection, and the server address is：\n$serverUrl"
        )
        stateMachineSubscriber.nextState()

        val request = Request.Builder().url(serverUrl!!).build()
        val okHttpClient = OkHttpClient.Builder().callTimeout(20, TimeUnit.SECONDS).build()
        webSocketClient = okHttpClient.newWebSocket(request, this)
    }

    fun disconnect() {
        val result = webSocketClient?.close(WebSocketStatusCode.CODE_NORMAL_CLOSURE, "OK") == true
        stateMachineSubscriber.nextState()
    }

    fun addConnectionStateObserver(observer: ConnectionStateObserver) {
        stateMachineSubscriber.addObserver(observer)
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)

        Log.i(
            TAG,
            "[Connection StatusConnection Statu]Successfully establish a connection, update the active time"
        )
        stateMachineSubscriber.nextState()
        stateMachineSubscriber.active()
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        super.onMessage(webSocket, bytes)

        Log.d(MessageAccessService.TAG, "onMessage: ")
        Log.d(MessageAccessService.TAG, "Received a message : " + EnvelopeHelper.extract(MessageDTO.Message.parseFrom(bytes.toByteArray()))?.messageVO?.content)

        val messageDTO = MessageDTO.Message.parseFrom(bytes.toByteArray())
        incomingMessagePipe.enqueueMessage(messageDTO)

        stateMachineSubscriber.active()
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        super.onFailure(webSocket, t, response)

        Log.w(TAG, "onFailure: Connection failed due to: \n${t.localizedMessage}")

        response?.takeIf { HttpStatusCode.isHttpStatusError(response.code) }
            ?.apply {
                stateMachineSubscriber.fail(
                    IllegalConnectionException(
                        code,
                        "Connection failed due to: ${HttpStatusCode.getStatusCodeDesc(code)} \n ${t.localizedMessage}"
                    )
                )
                return //HTTP错误不执行重连
            }

        stateMachineSubscriber.idle()
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosing(webSocket, code, reason)
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosed(webSocket, code, reason)
        Log.e(TAG, "[Connection Status]Closed，code is ${code}, reason：${reason}")

        stateMachineSubscriber.nextState()
        stateMachineSubscriber.removeAllObserver()

        webSocketClient = null
    }

    fun send(message: ByteString) {
        webSocketClient?.send(message)
    }

}