package com.madchan.imsdk.demo.impl

import androidx.lifecycle.ViewModelProvider
import com.madchan.imsdk.comp.ui.rongcloud.fragment.ConversationFragment

class ConversationFragmentImpl : ConversationFragment() {

    override fun getExtensionViewModel() = ViewModelProvider(this).get(ExtensionViewModelImpl::class.java)

}