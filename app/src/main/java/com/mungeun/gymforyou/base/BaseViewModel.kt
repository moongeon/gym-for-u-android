package com.mungeun.gymforyou.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.load.HttpException
import kotlinx.coroutines.CoroutineExceptionHandler
import java.net.ConnectException
import java.net.SocketException
import java.net.UnknownHostException

open class BaseViewModel : ViewModel() {

    protected val _fetchState = MutableLiveData<FetchState>()
    val fetchState : LiveData<FetchState>
        get() = _fetchState

    protected val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()

        when (throwable) {
            is SocketException -> _fetchState.postValue(FetchState.BAD_INTERNET)
            is HttpException -> _fetchState.postValue(FetchState.PARSE_ERROR)
            is UnknownHostException -> _fetchState.postValue(FetchState.WRONG_CONNECTION)
            is ConnectException -> _fetchState.postValue(FetchState.CONNECT_ERROR)
            else -> _fetchState.postValue(FetchState.FAIL)
        }
    }

    companion object{
        enum class FetchState{
            BAD_INTERNET,
            PARSE_ERROR,
            CONNECT_ERROR,
            WRONG_CONNECTION,
            FAIL
        }
    }
}