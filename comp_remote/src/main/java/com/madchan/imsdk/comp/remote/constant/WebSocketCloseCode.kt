package com.madchan.imsdk.comp.remote.constant

/**
 * ## Websocket连接关闭码定义
 *
 * 客户端已连接上聊天服务器，Websocket连接已创建成功，中途发生错误时服务端会主动断开与客户端的Websocket连接，客户端将会收到以下的Websocket关闭码。
 *
 * 详情请参考[Websocket连接错误码定义](https://cf.work.yunyugz.com/pages/viewpage.action?pageId=3965010)
 */
class WebSocketCloseCode {

    companion object {
        /**
         * 关闭码：账号下线导致服务端主动关闭会话连接
         *
         * 对于同一个账号，如果在相同设置（手机、平板、PC）登录多次，服务端只保留最后一次登录会话，
         * 旧的登录会话则会被服务端主动关闭连接（踢下线）
         */
        private const val CODE_ACCOUNT_OFFLINE = 4010

        /**
         * 关闭码：超出20秒未发送过任何数据包处于闲置状态被服务端主动关闭会话连接
         *
         * 心跳检查机制，如果客户端超出20秒从未发送过任何数据包，则认为是闲置处于假死状态的会话连接，
         * 服务端会主动关闭（如果还在线）连接
         */
        private const val CODE_IDLE_STATE = 4011

        /**
         * 关闭码：无效的消息包
         *
         * 如果发送的数据包无法被服务端解读，则服务端会认为此连接是非法连接，则会主动关闭连接。
         * 如客户端发送的消息包不是由protobuf定义的消息体，则属于非法！
         * 因此，客户端在发送数据包时需要严格按照消息包定义文档构造消息包数据。
         */
        const val CODE_INVALID_MESSAGE = 4020

        /**
         * 关闭码：无效的消息类型
         *
         * 如果发送的消息类型是服务端不认可的，则服务端会认为此连接是非法连接，则会主动关闭连接。
         * 如客户端发送系统通知消息包(type=5)，则属于非法！
         * 因此，客户端在发送数据包时需要严格按照消息包定义文档构造消息包数据。
         */
        private const val CODE_INVALID_MESSAGE_TYPE = 4021

        /**
         * 关闭码：聊天消息目标为空
         *
         * 发送聊天消息时，如果不带有任何用户id（单聊）或者群id（群聊），则服务端会认为此连接是非法连接，则会主动关闭连接。
         */
        private const val CODE_EMPTY_TARGET = 4022

        /**
         * 关闭码: 消息多次没有收到客户端的ACK回应
         *
         * 发送聊天消息时，如果客户端多次不对消息进行ACK回应，则服务端会认为此连接是非法连接，则会主动关闭连接。
         */
        private const val CODE_NO_RECEIVED_ACK = 4023

        /**
         * 根据closeCode获取对应描述
         * @param closeCode 关闭码
         * @return 关闭原因
         */
        fun getCloseCodeDesc(closeCode: Int): String {
            return when (closeCode) {
                CODE_ACCOUNT_OFFLINE -> "账号下线导致服务端主动关闭会话连接"
                CODE_IDLE_STATE -> "超出20秒未发送过任何数据包处于闲置状态被服务端主动关闭会话连接"
                CODE_INVALID_MESSAGE -> "无效的消息包"
                CODE_INVALID_MESSAGE_TYPE -> "无效的消息类型"
                CODE_EMPTY_TARGET -> "聊天消息目标为空"
                CODE_NO_RECEIVED_ACK -> "消息多次没有收到客户端的ACK回应"
                else -> "原因未明"
            }
        }

        /**
         * 是否是消息体错误
         * @param closeCode 关闭码
         * @return
         */
        fun isMessageBodyError(closeCode: Int): Boolean {
            return CODE_INVALID_MESSAGE == closeCode || CODE_INVALID_MESSAGE_TYPE == closeCode || CODE_EMPTY_TARGET == closeCode
        }

        /**
         * 指定关闭码场景是否允许重连
         * @param closeCode
         * @return
         */
        fun allowReconnect(closeCode: Int): Boolean {
            return closeCode != CODE_ACCOUNT_OFFLINE
        }

    }
}