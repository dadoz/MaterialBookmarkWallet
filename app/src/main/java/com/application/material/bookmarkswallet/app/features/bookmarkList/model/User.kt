package com.application.material.bookmarkswallet.app.features.bookmarkList.model

import androidx.annotation.Keep

@Keep
data class User(
    val name: String,
    val photoUrl: String? = null,
    val email: String,
    val userId: String,
    val idToken: String,
    val lastName: String,
    val firstName: String,
    val displayName: String,
)