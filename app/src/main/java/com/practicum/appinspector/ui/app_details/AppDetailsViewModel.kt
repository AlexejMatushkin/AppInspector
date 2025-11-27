package com.practicum.appinspector.ui.app_details

import android.app.Application
import androidx.lifecycle.*
import com.practicum.appinspector.util.HashUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AppDetailsViewModel(
    application: Application,
    packageName: String
) : AndroidViewModel(application) {

    private val pm = application.packageManager

    val name = MutableLiveData<String>()
    val version = MutableLiveData<String>()
    val checksum = MutableLiveData<String>()
    private var apkPath: String

    init {
        val info = pm.getPackageInfo(packageName, 0)
        val appInfo = pm.getApplicationInfo(packageName, 0)

        name.value = appInfo.loadLabel(pm).toString()
        version.value = info.versionName ?: ""
        apkPath = appInfo.sourceDir

        viewModelScope.launch(Dispatchers.IO) {
            checksum.postValue(HashUtils.sha1(apkPath))
        }
    }
}

@Suppress("UNCHECKED_CAST")
class AppDetailsFactory(
    private val app: Application,
    private val pkg: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AppDetailsViewModel(app, pkg) as T
    }
}
