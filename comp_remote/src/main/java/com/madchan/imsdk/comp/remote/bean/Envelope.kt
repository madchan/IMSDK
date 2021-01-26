package com.madchan.imsdk.comp.remote.bean

import android.os.Parcel
import android.os.Parcelable
import com.madchan.imsdk.lib.objects.bean.vo.MessageVo
import com.madchan.imsdk.lib.objects.bean.vo.NoticeVo
import kotlinx.android.parcel.Parcelize

/**
* 用于多进程通讯的信封类
* <p>
* 在AIDL中传递的对象，需要在类文件相同路径下，创建同名、但是后缀为.aidl的文件，并在文件中使用parcelable关键字声明这个类；
* 但实际业务中需要传递的对象所属的类往往分散在不同的模块，所以通过构建一个包装类来包含真正需要被传递的对象(必须也实现Parcelable接口)
*/
data class Envelope(val messageVo: MessageVo? = null,
                    val noticeVo: NoticeVo? = null) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readParcelable(MessageVo::class.java.classLoader),
        parcel.readParcelable(NoticeVo::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(messageVo, flags)
        parcel.writeParcelable(noticeVo, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Envelope> {
        override fun createFromParcel(parcel: Parcel): Envelope {
            return Envelope(parcel)
        }

        override fun newArray(size: Int): Array<Envelope?> {
            return arrayOfNulls(size)
        }
    }
}
