package com.madchan.imsdk.lib.objects.bean.vo

import android.os.Parcel
import android.os.Parcelable

data class MessageVO(
    var messageId: Long? = null,
    var messageType: Int? = null,
    var senderId: String? = null,
    var targetId: String? = null,
    var timestamp: Long? = null,
    var content: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(messageId)
        parcel.writeValue(messageType)
        parcel.writeString(senderId)
        parcel.writeString(targetId)
        parcel.writeValue(timestamp)
        parcel.writeString(content)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MessageVO> {
        override fun createFromParcel(parcel: Parcel): MessageVO {
            return MessageVO(parcel)
        }

        override fun newArray(size: Int): Array<MessageVO?> {
            return arrayOfNulls(size)
        }
    }
}