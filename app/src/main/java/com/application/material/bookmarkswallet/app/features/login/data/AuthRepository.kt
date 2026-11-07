package com.application.material.bookmarkswallet.app.features.login.data

import android.content.Context
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.application.material.bookmarkswallet.app.BuildConfig
import com.application.material.bookmarkswallet.app.BuildConfig.SERVER_CLIENT_ID
import com.application.material.bookmarkswallet.app.features.bookmarkList.model.User
import com.application.material.bookmarkswallet.app.storage.DataStoreManager
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import timber.log.Timber


@Singleton
class AuthRepository @Inject constructor(
    val dataStore: DataStoreManager
) {
    //woaaaaaa storing
    var userFlow: Flow<User?> = dataStore.userFlow
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    suspend fun signInWithCredential(context: Context): Result<User?> =
        try {
            Timber.e("----->" + SERVER_CLIENT_ID)
            val googleIdOption = GetGoogleIdOption.Builder()
                .setServerClientId(BuildConfig.SERVER_CLIENT_ID)
                .setFilterByAuthorizedAccounts(false)
                .build()

            val user = CredentialManager.create(context)
                .getCredential(
                    request = GetCredentialRequest.Builder()
                        .addCredentialOption(googleIdOption)
                        .build(),
                    context = context
                )
                .credential
                .let {
                    parseGoogleIdToken(
                        credential = it
                    )
                }
            Result.success(user)
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            Timber.e(e, "Error during credential sign in")
            Result.failure(e)
        }

    suspend fun setUser(user: User) {
        //set user to repo
        this.userFlow = flow {
            emit(user)
        }
        //store in datastore
        dataStore.saveUser(
            user = user
        )
    }

    suspend fun signInWithEmail(email: String, pass: String): Result<FirebaseUser?> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, pass).await()
            Result.success(result.user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun signUpWithEmail(email: String, pass: String): Result<FirebaseUser?> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, pass).await()
            Result.success(result.user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun signOut() {
        auth.signOut()
    }
}

fun parseGoogleIdToken(credential: Credential): User? {
    try {
        // Parsa i dati dal bundle della credenziale
        val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)

        val googleIdToken = googleIdTokenCredential.idToken
        val email =
            googleIdTokenCredential.id // L'ID corrisponde solitamente all'email dell'utente
        val firstName = googleIdTokenCredential.givenName
        val lastName = googleIdTokenCredential.familyName
        val displayName = googleIdTokenCredential.displayName
        val profilePictureUri = googleIdTokenCredential.profilePictureUri

        // Mappa i dati in una tua Data Class utente personalizzata
        val user = User(
            email = email,
            displayName = displayName ?: "-",
            firstName = firstName ?: "-",
            lastName = lastName ?: "-",
            idToken = googleIdToken,
            photoUrl = profilePictureUri?.toString(),
            name = "bla",
            userId = "blala",
        )
        return user
    } catch (e: GoogleIdTokenParsingException) {
        // Errore durante il parsing del token
        e.printStackTrace()
        return null
    }
}