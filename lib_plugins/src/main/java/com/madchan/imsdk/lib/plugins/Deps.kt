package com.madchan.imsdk.lib.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project

class Deps : Plugin<Project> {

    override fun apply(project: Project) {
    }

    companion object {
        const val LIB_BASE = "com.litalk.supportlib:lib_base:1.0.0-alpha15"
        const val LIB_NETWORK = "com.litalk.supportlib:lib_network:1.0.0-alpha01"
    }
}