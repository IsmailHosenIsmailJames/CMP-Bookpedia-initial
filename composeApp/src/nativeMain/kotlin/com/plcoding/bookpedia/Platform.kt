package com.plcoding.bookpedia

import platform.UIKit.UIDevice

// The NativePlatform class might be used by other native targets,
// or it might need to be removed if not used elsewhere and iosMain provides the Apple-specific one.
// For now, only removing the conflicting actual function.
class NativePlatform : Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

// actual fun getPlatform(): Platform = NativePlatform() // Removed this conflicting actual
