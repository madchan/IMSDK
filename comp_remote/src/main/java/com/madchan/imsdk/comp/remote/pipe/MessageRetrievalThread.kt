package com.madchan.imsdk.comp.remote.pipe

import com.madchan.imsdk.comp.remote.bean.Envelope
import com.madchan.imsdk.comp.remote.util.EnvelopeHelper

class MessageRetrievalThread(
    private val pipe: IncomingMessagePipe,
    private val callback: MessageRetrievalCallback
) : Thread() {

    private var terminated = false;

    override fun run() {
        super.run()
        try {
            while (!isTerminated()) {
                val envelope = EnvelopeHelper.extract(pipe.dequeueMessage())
                callback.onReadMessage(envelope)
            }
        } catch (e: InterruptedException) {

        } finally {

        }
    }

    interface MessageRetrievalCallback {
        fun onReadMessage(envelope: Envelope?)
    }

    fun terminateAsync() {
        terminated = true
        interrupt()
    }

    private fun isTerminated() = terminated
}