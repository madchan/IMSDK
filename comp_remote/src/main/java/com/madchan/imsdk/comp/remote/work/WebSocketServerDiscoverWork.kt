package com.madchan.imsdk.comp.remote.work

import android.content.Context
import androidx.work.*
import com.madchan.imsdk.comp.remote.constant.RemoteDataStoreKey
import com.litalk.supportlib.lib.base.util.DataStoreUtil
import com.madchan.imsdk.comp.base.SDKBase
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
            WorkManager.getInstance(SDKBase.dependentContext).enqueue(request)

            WorkManager.getInstance(SDKBase.dependentContext).getWorkInfoByIdLiveData(request.id)
                .observeForever {
                    when(it.state) {
                        WorkInfo.State.SUCCEEDED -> MessageAccessServiceProvider.setupService(SDKBase.dependentContext)
                    }
                }
        }
    }

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        val mockServer = "wss://echo.websocket.org"
        DataStoreUtil.writeString(SDKBase.dependentContext, RemoteDataStoreKey.WEB_SOCKET_SERVER_URL, mockServer)
        Result.success()
    }

}