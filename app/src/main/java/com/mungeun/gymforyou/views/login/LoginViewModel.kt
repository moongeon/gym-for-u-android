package com.mungeun.gymforyou.views.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mungeun.domain.usecase.LoginUseCase
import com.mungeun.gymforyou.R
import com.mungeun.gymforyou.base.BaseViewModel
import com.mungeun.gymforyou.utilities.Event
import com.mungeun.gymforyou.utilities.message.SnackbarMessage
import com.mungeun.gymforyou.utilities.preference.PreferenceManger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val preferenceManger: PreferenceManger,
) : BaseViewModel() {
    val id: MutableLiveData<String> = MutableLiveData("")
    val pw: MutableLiveData<String> = MutableLiveData("")
    val gif =""
//        "https://noticon-static.tammolo.com/dgggcrkxq/image/upload/v1616137592/noticon/mllppshbmymxfgbm1jod.gif"


    // 메시지
    private val _snackbarMessage = MutableLiveData<Event<SnackbarMessage>>()
    val snackBarMessage: LiveData<Event<SnackbarMessage>>
        get() = _snackbarMessage


    private val _isIdEmpty: MutableLiveData<Unit> = MutableLiveData()
    private val _isPwEmpty: MutableLiveData<Unit> = MutableLiveData()
    private val _loginErrorMsg: MutableLiveData<Unit> = MutableLiveData()


    private val _successLogin = MutableLiveData<Event<Boolean>>()
    val successLogin: LiveData<Event<Boolean>>
        get() = _successLogin

    private val _goSignUp = MutableLiveData<Event<Boolean>>()
    val goSignUp: LiveData<Event<Boolean>>
        get() = _goSignUp

    val isIdEmpty: LiveData<Unit> get() = _isIdEmpty
    val isPwEmpty: LiveData<Unit> get() = _isPwEmpty
    val loginErrorMsg: LiveData<Unit> get() = _loginErrorMsg

    fun goSignUp() {
        _goSignUp.postValue(Event(true))
    }


    fun onLoginClick() {
        val id = id.value.toString().trim()
        val pw = pw.value.toString().trim()
        if (id.isEmpty()) {
            _snackbarMessage.postValue(
                Event(
                    SnackbarMessage(
                        messageId = "아이디를 입력해주세요.",
                        actionId = R.string.ok,
                        requestChangeId = UUID.randomUUID().toString()
                    )
                )
            )
        } else if (pw.isEmpty()) {
            _snackbarMessage.postValue(
                Event(
                    SnackbarMessage(
                        messageId = "비밀번호를 입력해주세요.",
                        actionId = R.string.ok,
                        requestChangeId = UUID.randomUUID().toString()
                    )
                )
            )
        } else {
            viewModelScope.launch(exceptionHandler) {
                showProgress()
                val response = loginUseCase.invoke(id, pw)
                with(preferenceManger) {
                    accessToken = response.accessToken
                    refreshToken = response.refreshToken
                    userName = id
                }
                showProgress()
                _successLogin.postValue(Event(true))
            }

        }

    }

    companion object { //이 아이디와 비번으로만 로그인이 가능 (서버X)
        private const val USER_ID = "mungeun"
        private const val USER_PW = "1234"
    }
}