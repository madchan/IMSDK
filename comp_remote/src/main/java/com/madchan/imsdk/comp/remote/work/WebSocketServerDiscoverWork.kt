package com.madchan.imsdk.comp.remote.work

import android.content.Context
import androidx.work.*
import com.madchan.imsdk.comp.remote.constant.RemoteDataStoreKey
import com.litalk.supportlib.lib.base.util.DataStoreUtil
import com.madchan.imsdk.comp.base.SDKCache
import com.madchan.imsdk.comp.remote.MessageAccessServiceProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * WebSocket服务器地址发现Work
 */
class WebSocketServerDiscoverWork(context: Context, workerParameters: WorkerParameters) : CoroutineWorker(context, workerParameters) {

    companion object {
        fun enqueueAndObserve() {
            val request = OneTimeWorkRequestBuilder<WebSocketServerDiscoverWork>()
                .build()
            WorkManager.getInstance(SDKCache.context).enqueue(request)

            WorkManager.getInstance(SDKCache.context).getWorkInfoByIdLiveData(request.id)
                .observeForever {
                    when(it.state) {
                        WorkInfo.State.SUCCEEDED -> MessageAccessServiceProvider.setupService(SDKCache.context)
                    }
                }
        }
    }

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
//        val mockServer = "wss://echo.websocket.org"
        val mockServer = "ws://172.16.84.82:8080/websocket"
        DataStoreUtil.writeString(SDKCache.context, RemoteDataStoreKey.WEB_SOCKET_SERVER_URL, mockServer)
        Result.success()
    }

}