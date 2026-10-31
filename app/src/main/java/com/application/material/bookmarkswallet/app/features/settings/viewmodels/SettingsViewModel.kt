package com.application.material.bookmarkswallet.app.features.settings.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.application.material.bookmarkswallet.app.features.bookmarkList.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application = application) {
    val user by lazy {
        User(
            name = "Davide Lm",
            userId = "-",
            email = "-",
            photoUrl = null,
            idToken = "id token",
            lastName = "last name",
            firstName = "first name",
            displayName = "Davide L"
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