package com.application.material.bookmarkswallet.app.features.settings.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.application.material.bookmarkswallet.app.features.bookmarkList.model.User
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application = application) {
    val user by lazy {
        null
            ?: User(
                name = "GUEST",
                userId =  "-",
                email = "-",
                photoUrl = null,
                idToken = TODO(),
                lastName = TODO(),
                firstName = TODO(),
                displayName = TODO()
            )
    }

//    fun signIn(email: String, password: String) {
//        auth = FirebaseAuth.getInstance()
//        auth.signInWithEmailAndPassword(email, password)
//    }
//
//    fun signOut() {
//        auth = FirebaseAuth.getInstance()
//        auth.signOut()
//    }

}