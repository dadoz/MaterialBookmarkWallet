package com.application.material.bookmarkswallet.app.features.hp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.application.material.bookmarkswallet.app.features.bookmarkList.model.User
import com.application.material.bookmarkswallet.app.features.login.data.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class MainViewModel @Inject constructor(
    application: Application,
    authRepository: AuthRepository,
) :
    AndroidViewModel(application = application) {

    val userState: StateFlow<UserState> =
        authRepository.userFlow
            .map {
                when {
                    it == null -> UserState(
                        state = UserState.Init()
                    )

                    else -> UserState(
                        state = UserState.LoggedIn(
                            user = it
                        )
                    )
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStartedWhileSubscr(),
                initialValue = UserState()
            )
}

//todo handle it
data class UserState(val state: Any = Loading()) {
    data class LoggedIn(
        val user: User? = null
    )

    class Init()

    class Loading()
}

fun SharingStartedWhileSubscr() = SharingStarted.WhileSubscribed(5000)
