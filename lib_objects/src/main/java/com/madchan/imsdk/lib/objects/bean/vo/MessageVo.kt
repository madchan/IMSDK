package com.madchan.imsdk.lib.objects.bean.vo

import android.os.Parcel
import android.os.Parcelable

data class MessageVo(val content: String? = null) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(content)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MessageVo> {
        override fun createFromParcel(parcel: Parcel): MessageVo {
            return MessageVo(parcel)
        }

        override fun newArray(size: Int): Array<MessageVo?> {
            return arrayOfNulls(size)
        }
    }
}