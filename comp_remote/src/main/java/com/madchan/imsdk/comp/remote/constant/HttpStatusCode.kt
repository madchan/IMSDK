package com.madchan.imsdk.comp.remote.constant

/**
 * ## 连接聊天服务的HTTP错误状态码定义
 *
 * 客户端通过获取消息聊天服务器接口获取Websocket地址尝试连接聊天服务器，
 * 如果请求有误则Websocket创建不成功，客户端会收到以下HTTP的错误状态码：
 *
 * 详情请参考：[连接聊天服务的HTTP错误状态码定义：](https://cf.work.yunyugz.com/pages/viewpage.action?pageId=3965010)
 */
class HttpStatusCode {

    companion object {
        /**
         * 客户端通过获取消息聊天服务器接口获取Websocket地址尝试连接聊天服务器，如果请求有误则Websocket创建不成功，客户端会收到以下HTTP的错误状态码：
         * 错误的请求参数，WS地址的附加参数有误，客户端需要重新调用接口获取新的聊天服务器地址
         */
        const val HTTP_STATUS_CODE_ERROR_PARAMS = 400

        /** 用户Token无效或者已过期。客户端需要退出要求重登录  */
        private const val HTTP_STATUS_CODE_TOKEN_INVALID = 401

        /** 用户数据有误，如账号已禁用。客户端需要退出要求重登录  */
        private const val HTTP_STATUS_CODE_USER_DATA_ERROR = 423 // 为了与API接口那边区分开，原403改为423

        /**
         * 根据type获取对应关HTTP错误状态码定义
         * @param statusCode HTTP错误状态码
         * @return 错误原因
         */
        fun getStatusCodeDesc(statusCode: Int): String {
            return when (statusCode) {
                HTTP_STATUS_CODE_ERROR_PARAMS -> "错误的请求参数，WS地址的附加参数有误，客户端需要重新调用接口获取新的聊天服务器地址"
                HTTP_STATUS_CODE_TOKEN_INVALID -> "用户Token无效或者已过期。客户端需要退出要求重登录"
                HTTP_STATUS_CODE_USER_DATA_ERROR -> "用户数据有误，如账号已禁用。客户端需要退出要求重登录"
                else -> ""
            }
        }

        /**
         * 是否是Http请求错误
         * @param statusCode 错误码
         * @return
         */
        fun isHttpStatusError(statusCode: Int): Boolean {
            return HTTP_STATUS_CODE_ERROR_PARAMS == statusCode || HTTP_STATUS_CODE_TOKEN_INVALID == statusCode || HTTP_STATUS_CODE_USER_DATA_ERROR == statusCode
        }

        /**
         * 客户端是否需要退出要求重登录
         * @param statusCode 错误码
         * @return
         */
        fun forceReLogin(statusCode: Int): Boolean {
            return HTTP_STATUS_CODE_TOKEN_INVALID == statusCode ||
                    HTTP_STATUS_CODE_USER_DATA_ERROR == statusCode
        }

        /**
         * 获取Http错误码对应的被踢下线的原因
         * @param statusCode 错误码
         * @return
         */
        fun getOfflineReasonValue(statusCode: Int): Int {
            return when (statusCode) {
//                HTTP_STATUS_CODE_TOKEN_INVALID -> WebSocketProtos.UserOfflineMessage.Reason.TOKEN_EXPIRED_VALUE
//                HTTP_STATUS_CODE_USER_DATA_ERROR -> WebSocketProtos.UserOfflineMessage.Reason.LOCKED_VALUE
                else -> 0
            }
        }

    }

}