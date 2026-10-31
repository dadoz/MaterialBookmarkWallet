package com.application.material.bookmarkswallet.app.features.login.viewmodel

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.application.material.bookmarkswallet.app.features.bookmarkList.model.User
import com.application.material.bookmarkswallet.app.features.login.data.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.OAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class LoginViewModel @Inject constructor(
    application: Application,
    val authRepository: AuthRepository,
) :
    AndroidViewModel(application = application) {
    private var isLoading: Boolean = false

    fun handleGoogleSignInResult(context: Context?, onComplete: (User?, Throwable?) -> Unit) {
        context
            ?.also { context ->
                viewModelScope.launch {
                    authRepository.signInWithCredential(context = context)
                        .also {
                            when {
                                it.isSuccess -> {
                                    it.getOrThrow()
                                        ?.also { user ->
                                            Timber.e(
                                                user.toString()
                                            )
                                            //store in repository
                                            authRepository.setUser(
                                                user = user
                                            )
                                            //call callback
                                            onComplete.invoke(user, null)
                                        }
                                }

                                it.isFailure -> onComplete.invoke(null, it.exceptionOrNull())
                            }
                        }
                }
            }
    }

    fun signInWithApple(activity: Activity, onResult: (Result<FirebaseUser?>) -> Unit) {
        val provider = OAuthProvider.newBuilder("apple.com")
        // Puoi aggiungere parametri opzionali come la lingua o gli scope
        provider.scopes = listOf("email", "name")

        FirebaseAuth.getInstance()
            .startActivityForSignInWithProvider(activity, provider.build())
            .addOnSuccessListener { authResult ->
                onResult(Result.success(authResult.user))
            }
            .addOnFailureListener { exception ->
                onResult(Result.failure(exception))
            }
    }

    // Chiamata dal ViewModel
    fun loginWithEmail(email: String, pass: String) {
        viewModelScope.launch {
            isLoading = true
            val result = authRepository.signInWithEmail(email, pass)
            isLoading = false

            if (result.isSuccess) {
                // Naviga alla mappa di AquaZero
            } else {
                // Mostra errore (es. credenziali errate)
            }
        }
    }

}