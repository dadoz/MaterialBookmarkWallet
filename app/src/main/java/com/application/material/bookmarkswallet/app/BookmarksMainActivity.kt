package com.application.material.bookmarkswallet.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.compose.rememberNavController
import com.application.material.bookmarkswallet.app.features.hp.viewmodel.MainViewModel
import com.application.material.bookmarkswallet.app.features.hp.viewmodel.UserState
import com.application.material.bookmarkswallet.app.ui.MaterialBookmarkMaterialTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.generated.NavGraphs
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import kotlin.getValue

@AndroidEntryPoint
class BookmarksMainActivity : ComponentActivity(),
    LifecycleOwner {
    val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

//        //todo remove this
//        auth = Firebase.auth
//        authAnonymousWithFirebase()

        //content
        setContent {
            val userState by mainViewModel.userState.collectAsState()
            splashScreen.setKeepOnScreenCondition {
                userState.state is UserState.Loading
            }
            MaterialBookmarkMaterialTheme {
                //init hp view
                AppContentAndNavigation()
            }
        }
    }

    @Composable
    fun AppContentAndNavigation(
        mainViewModel: MainViewModel = hiltViewModel<MainViewModel>(),
    ) {
        val userState by mainViewModel.userState.collectAsState()
        val navController = rememberNavController()

        Timber.e("user: $userState")
        LaunchedEffect(key1 = userState) {
            //todo please fix Splash but is not a splash - never logging in this way
//            when (userState.state) {
//                is UserState.Loading -> {
//                    Timber.e("loading")
//
//                }
//
//                is UserState.LoggedIn -> navController.navigate(DashboardViewDestination.baseRoute)
//
//                is UserState.Init -> {
//                    Timber.e("Init")
//                }
//            }
        }
        DestinationsNavHost(
            navGraph = NavGraphs.root,
            navController = navController
        )
    }

//
//    public override fun onStart() {
//        super.onStart()
//        // Check if user is signed in (non-null) and update UI accordingly.
//        val user = auth.currentUser
//        Timber.e(
//            "%s - %s", user?.email, user?.uid
//        )
//    }
//
//    fun authAnonymousWithFirebase() {
//        auth.signInAnonymously()
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    // Sign in success, update UI with the signed-in user's information
//                    Timber.e("signInAnonymously:success")
//                    val user = auth.currentUser
//                    Timber.e(
//                        "%s - %s", user?.email, user?.uid
//                    )
//
//                } else {
//                    // If sign in fails, display a message to the user.
//                    Timber.e("signInAnonymously:failure ${task.exception}")
//                    Toast.makeText(
//                        baseContext,
//                        "Authentication failed.",
//                        Toast.LENGTH_SHORT,
//                    ).show()
//                    Timber.e(
//                        "FAILED TO LOAD USER"
//                    )
//                }
//            }

}
