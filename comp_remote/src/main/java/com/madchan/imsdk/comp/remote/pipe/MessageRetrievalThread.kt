package com.madchan.imsdk.comp.remote.pipe

import com.madchan.imsdk.comp.remote.bean.Envelope
import com.madchan.imsdk.comp.remote.util.EnvelopeHelper

/**
 * 消息检索线程
 */
class MessageRetrievalThread(
    private val pipe: IncomingMessagePipe,   // 收到消息管道
    private val callback: MessageRetrievalCallback      // 消息检索回调
) : Thread() {

    /** 线程终止标识 */
    private var terminated = false;

    override fun run() {
        super.run()
        try {
            // 循环从消息管道取出消息，包装成信封对象并回调
            while (!isTerminated()) {
                val envelope = EnvelopeHelper.extract(pipe.dequeueMessage())
                callback.onReadMessage(envelope)
            }
        } catch (e: InterruptedException) {

        } finally {

        }
    }

    /**
     * 消息检索回调
     */
    interface MessageRetrievalCallback {
        fun onReadMessage(envelope: Envelope?)
    }

    /**
     * 发出终止处理
     */
    fun terminateAsync() {
        terminated = true
        interrupt()
    }

    /**
     * 是否已发出终止处理
     */
    private fun isTerminated() = terminated
}