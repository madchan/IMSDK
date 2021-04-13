package com.madchan.imsdk.comp.remote.observer

import com.madchan.imsdk.comp.remote.constant.ConnectionStateMachine
import com.madchan.imsdk.comp.remote.exception.IllegalConnectionException
import com.litalk.supportlib.lib.base.util.LogUtil

/**
 * ## WebSocket连接状态机
 *
 * 使用枚举实现状态机以优雅处理状态变更逻辑。
 *
 * 状态机定义了一套状态変更的流程：状态机包含一个有限个状态集合，定义当状态机处于某一个状态的时候
 * 它所能接收的事件以及可执行的行为，执行完成后，状态机所处的状态变化可以被感知。
 */
class ConnectionStateMachineSubscriber {

    /** WebSocket连接状态机 */
    private var stateMachine = ConnectionStateMachine.IDLE

    /** 观察者集合 */
    private val observerList: MutableList<ConnectionStateObserver> = mutableListOf()

    fun nextState() {
        stateMachine = stateMachine.nextState()
        LogUtil.d(msg = "Current connection state is $stateMachine")
        notifyAllObserver()
    }

    fun idle() {
        stateMachine = ConnectionStateMachine.IDLE
        notifyAllObserver()
    }

    fun fail(exception: IllegalConnectionException) {
        stateMachine = ConnectionStateMachine.IDLE
        for (observer in observerList) {
            observer.onFailure(exception)
        }
    }

    fun active() {
        for (observer in observerList) {
            observer.onActive()
        }
    }

    fun isIdle() = ConnectionStateMachine.IDLE == stateMachine

    fun addObserver(observer: ConnectionStateObserver) {
        observerList.add(observer)
    }

    private fun notifyAllObserver() {
        for (observer in observerList) {
            observer.onChange(stateMachine)
        }
    }

    fun removeAllObserver() {
        observerList.clear()
    }

}