package com.madchan.imsdk.lib.objects.bean.vo

import android.os.Parcel
import android.os.Parcelable

class NoticeVO() : Parcelable {
    constructor(parcel: Parcel) : this() {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NoticeVO> {
        override fun createFromParcel(parcel: Parcel): NoticeVO {
            return NoticeVO(parcel)
        }

        override fun newArray(size: Int): Array<NoticeVO?> {
            return arrayOfNulls(size)
        }
    }
}