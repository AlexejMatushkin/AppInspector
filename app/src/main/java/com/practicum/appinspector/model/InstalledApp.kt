package com.practicum.appinspector.model

data class InstalledApp(
    val appName: String,
    val packageName: String,
    val versionName: String,
    val apkPath: String
)
