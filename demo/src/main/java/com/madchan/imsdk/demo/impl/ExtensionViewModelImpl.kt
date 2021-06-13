package com.madchan.imsdk.demo.impl

import com.madchan.imsdk.comp.ui.rongcloud.extension.RongExtensionViewModel
import com.madchan.imsdk.demo.FakeUserInfoManager
import com.madchan.imsdk.sdk.IMMessageCreator
import com.madchan.imsdk.sdk.IMMessageSender

class ExtensionViewModelImpl: RongExtensionViewModel() {

    override fun onSendClick() {
        super.onSendClick()

        val text = mEditText.text.toString()
        mEditText.setText("")

        FakeUserInfoManager.userInfo?.id?.let { senderId->
            IMMessageSender.sendMessage(IMMessageCreator.createTextMessage(senderId, mTargetId, text))
        }
    }
}