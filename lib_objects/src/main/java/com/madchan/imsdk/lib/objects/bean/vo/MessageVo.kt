package com.madchan.imsdk.lib.objects.bean.vo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MessageVo(val content: CharSequence) : Parcelable {
}