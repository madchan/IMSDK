package com.madchan.imsdk.comp.base

import android.content.Context
import kotlin.properties.Delegates

class SDKBase {
    companion object {
        /** 接入方的全局Application上下文 */
        var dependentContext by Delegates.notNull<Context>()
    }
}