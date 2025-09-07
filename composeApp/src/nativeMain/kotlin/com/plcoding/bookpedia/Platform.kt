package com.plcoding.bookpedia

import platform.UIKit.UIDevice

class NativePlatform : Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = NativePlatform()
