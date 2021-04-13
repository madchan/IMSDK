package com.madchan.imsdk.comp.remote.observer

import com.madchan.imsdk.comp.remote.constant.ConnectionStateMachine
import com.madchan.imsdk.comp.remote.exception.IllegalConnectionException

interface ConnectionStateObserver {
    fun onChange(stateMachine: ConnectionStateMachine)
    fun onFailure(exception: IllegalConnectionException)
    fun onActive()
}