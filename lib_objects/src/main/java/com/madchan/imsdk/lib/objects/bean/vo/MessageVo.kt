package com.madchan.imsdk.lib.objects.bean.vo

import android.os.Parcel
import android.os.Parcelable

data class MessageVo(
    var messageId: Long,
    var messageType: Int,
    var sendId: String,
    var targetId: String,
    var timestamp: Long,
    var content: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readLong(),
        parcel.readString() ?: ""
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(messageId)
        parcel.writeInt(messageType)
        parcel.writeString(sendId)
        parcel.writeString(targetId)
        parcel.writeLong(timestamp)
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