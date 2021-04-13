package com.madchan.imsdk.lib.objects.bean.vo

import android.os.Parcel
import android.os.Parcelable

class NoticeVo() : Parcelable {
    constructor(parcel: Parcel) : this() {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NoticeVo> {
        override fun createFromParcel(parcel: Parcel): NoticeVo {
            return NoticeVo(parcel)
        }

        override fun newArray(size: Int): Array<NoticeVo?> {
            return arrayOfNulls(size)
        }
    }
}