package com.madchan.imsdk.comp.remote.constant

/**
 * ## WebSocket连接状态机
 *
 * 使用枚举实现状态机以优雅处理状态变更逻辑。
 *
 * 状态机定义了一套状态変更的流程：状态机包含一个有限个状态集合，定义当状态机处于某一个状态的时候
 * 它所能接收的事件以及可执行的行为，执行完成后，状态机所处的状态变化可以被感知。
 */
enum class ConnectionStateMachine() {

    IDLE {
        override fun nextState(): ConnectionStateMachine = CONNECTING
    },
    CONNECTING {
        override fun nextState(): ConnectionStateMachine = CONNECTED
    },
    CONNECTED {
        override fun nextState(): ConnectionStateMachine = CLOSED
    },
    CLOSED {
        override fun nextState(): ConnectionStateMachine = IDLE
    };

    abstract fun nextState(): ConnectionStateMachine

}