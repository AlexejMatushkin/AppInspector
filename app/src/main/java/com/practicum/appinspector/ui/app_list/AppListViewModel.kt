package com.practicum.appinspector.ui.app_list

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.practicum.appinspector.model.InstalledApp

class AppListViewModel(application: Application) : AndroidViewModel(application) {

    private val pm = application.packageManager

    private val _apps = MutableLiveData<List<InstalledApp>>()
    val apps: LiveData<List<InstalledApp>> = _apps

    @SuppressLint("QueryPermissionsNeeded")
    fun load() {
        val list = pm.getInstalledPackages(0).map { pkg ->
            val appInfo = pm.getApplicationInfo(pkg.packageName, 0)

            InstalledApp(
                appName = appInfo.loadLabel(pm).toString(),
                packageName = pkg.packageName,
                versionName = pkg.versionName ?: "",
                apkPath = appInfo.sourceDir
            )
        }.sortedBy { it.appName }

        _apps.postValue(list)
    }
}
