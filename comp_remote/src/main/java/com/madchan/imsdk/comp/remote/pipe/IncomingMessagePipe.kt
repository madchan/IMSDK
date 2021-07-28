package com.madchan.imsdk.comp.remote.pipe

import com.madchan.imsdk.lib.objects.bean.dto.MessageDTO
import okhttp3.internal.notifyAll
import okhttp3.internal.wait
import java.util.*

class IncomingMessagePipe {

    private val mMessageQueue = LinkedList<MessageDTO.Message>()

    @Throws(InterruptedException::class)
    @Synchronized
    fun dequeueMessage(): MessageDTO.Message  {
        while(mMessageQueue.isEmpty()) {
            wait()
        }
        return mMessageQueue.removeFirst()
    }

    @Synchronized
    fun enqueueMessage(message: MessageDTO.Message) {
        mMessageQueue.add(message)
        notifyAll()
    }

}