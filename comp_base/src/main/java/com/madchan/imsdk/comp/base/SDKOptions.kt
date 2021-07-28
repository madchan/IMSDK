package com.madchan.imsdk.comp.base

/**
 * ## SDK配置选项
 * 用于配置IMSDK初始化时的各项参数
 */
data class SDKOptions(
    /** 接入方申请的AppKey */
    var appKey: String? = null
) {
}