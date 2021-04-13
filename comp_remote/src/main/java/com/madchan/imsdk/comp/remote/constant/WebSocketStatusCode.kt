package com.madchan.imsdk.comp.remote.constant

/**
 * ## WebSocket状态码定义
 *
 * 当关闭已建立的连接时，指示WebSocket连接关闭的原因以及客户端应该采取的处理
 *
 * 详情请参考[Section 7.4 of RFC 6455](http://tools.ietf.org/html/rfc6455#section-7.4).
 */
class WebSocketStatusCode {

    companion object {
        /**
         * 状态码：表示正常关闭，这意味着已建立连接
         */
        const val CODE_NORMAL_CLOSURE = 1000

    }
}