package com.madchan.imsdk.comp.remote.exception

import java.lang.RuntimeException

class IllegalConnectionException(code: Int, message: String): RuntimeException(message)