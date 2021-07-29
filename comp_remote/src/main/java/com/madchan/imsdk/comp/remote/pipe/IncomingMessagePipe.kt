package com.madchan.imsdk.comp.remote.pipe

import com.madchan.imsdk.lib.objects.bean.dto.MessageDTO
import okhttp3.internal.notifyAll
import okhttp3.internal.wait
import java.util.*

/** 收到消息管道 */
class IncomingMessagePipe {

    /** 消息队列 */
    private val mMessageQueue = LinkedList<MessageDTO.Message>()

    /**
     * 消息出队
     * @return 消息数据传输对象
     */
    @Throws(InterruptedException::class)
    @Synchronized
    fun dequeueMessage(): MessageDTO.Message  {
        while(mMessageQueue.isEmpty()) {
            wait()
        }
        return mMessageQueue.removeFirst()
    }

    /**
     * 消息入队
     * @param message 消息数据传输对象
     */
    @Synchronized
    fun enqueueMessage(message: MessageDTO.Message) {
        mMessageQueue.add(message)
        notifyAll()
    }

}