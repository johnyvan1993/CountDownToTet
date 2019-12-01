package com.johy.countdowntotet.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

open class BaseViewModel(app: Application) : AndroidViewModel(app), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + viewModelJob

//    val mApiManager: ApiManager by GlobalContext.get().koin.inject()
//    val mPrefsManager: PrefsManager by GlobalContext.get().koin.inject()

    val viewModelJob = SupervisorJob()
    val loadingLive: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>().apply { value = false } }

    fun startLoad() {
        loadingLive.postValue(true)
    }

    fun endLoad() {
        loadingLive.postValue(false)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    var mApiErrorCall: ((e: Exception) -> Unit)? = {
        endLoad()
    }

//    suspend fun <T : Any> handleCallApi(result: Result<T>, onSuccess: (t: T?) -> Unit, onError: ((e: Exception) -> Unit)? = mApiErrorCall) {
//        return when (result) {
//            is Result.Success -> {
//                onSuccess(result.data)
//                endLoad()
//            }
//            is Result.Error -> {
//                onError?.invoke(result.exception)!!
//            }
//        }
//    }
}