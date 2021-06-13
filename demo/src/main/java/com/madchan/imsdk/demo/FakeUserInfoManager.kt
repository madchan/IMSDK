package com.madchan.imsdk.demo

object FakeUserInfoManager {

    var userInfo: UserInfo? = UserInfo("001")

    data class UserInfo(var id: String? = null) {}
}