package com.application.material.bookmarkswallet.app.features.login

import android.app.Activity
import android.content.res.Configuration
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.application.material.bookmarkswallet.app.R
import com.application.material.bookmarkswallet.app.features.login.viewmodel.LoginViewModel
import com.application.material.bookmarkswallet.app.ui.MaterialBookmarkMaterialTheme
import com.application.material.bookmarkswallet.app.ui.components.MbCardView
import com.application.material.bookmarkswallet.app.ui.components.MbPrimaryButton
import com.application.material.bookmarkswallet.app.ui.style.Dimen
import com.application.material.bookmarkswallet.app.ui.style.MbColor
import com.application.material.bookmarkswallet.app.ui.style.MbYantramanavBoldFontFamily
import com.application.material.bookmarkswallet.app.ui.style.MbYantramanavRegularFontFamily
import com.application.material.bookmarkswallet.app.ui.style.homeBackgroundColor
import com.application.material.bookmarkswallet.app.ui.style.mbButtonBlackLightDarkColor
import com.application.material.bookmarkswallet.app.ui.style.mbButtonWhiteLightDarkColor
import com.application.material.bookmarkswallet.app.ui.style.mbButtonWhiteYellowDarkColor
import com.application.material.bookmarkswallet.app.ui.style.mbGrayLightExtraBlueDarkColor
import com.application.material.bookmarkswallet.app.ui.style.mbMustardDarkWhiteColor
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.DashboardViewDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import timber.log.Timber


@Destination<RootGraph>(start = true)
@Composable
fun LoginView(
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel = hiltViewModel<LoginViewModel>(),
    navigator: DestinationsNavigator = EmptyDestinationsNavigator,
) {
    val activity = LocalActivity.current

    //google login
    val onGoogleLogin: () -> Unit = {
        loginViewModel.handleGoogleSignInResult(
            context = activity,
            onComplete = { user, error ->
                when {
                    user != null -> navigator.navigate(
                        direction = DashboardViewDestination,
                    )

                    else -> {
                        Timber.e(error)
                    }
                }
            }
        )
    }
    val onAppleLoginClick: () -> Unit = {}
    val onGuestLoginClick: () -> Unit = {
        navigator.navigate(DashboardViewDestination)
    }
    val onTermsClick: () -> Unit = {}
    val onPrivacyClick: () -> Unit = {}

    Column(
        modifier = modifier
            .windowInsetsPadding(
                WindowInsets.systemBars
            )
            .fillMaxSize()
            .background(homeBackgroundColor())
            .verticalScroll(rememberScrollState())
            .padding(horizontal = Dimen.paddingLarge32dp)
            .padding(vertical = Dimen.paddingLarge32dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Top Section: App Logo & Headline
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            // App Logo Badge
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape)
                    .background(color = MbColor.DarkGray),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_bookmark),
                    contentDescription = null,
                    tint = MbColor.Yellow,
                    modifier = Modifier.size(48.dp)
                )
            }

            // Headline
            Text(
                text = "Salva, organizza, esplora",
                style = TextStyle(
                    color = mbMustardDarkWhiteColor(),
                    fontSize = 26.sp,
                    fontFamily = MbYantramanavBoldFontFamily,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            )

            // Subtitle
            Text(
                text = "Bookmarks preferiti, ovunque tu sia\ndisponibili sul tuo telefono.",
                style = TextStyle(
                    color = if (isSystemInDarkTheme()) Color(0xFFB0B0A8) else Color(0xFF443300),
                    fontSize = 14.sp,
                    fontFamily = MbYantramanavRegularFontFamily,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .padding(horizontal = Dimen.paddingMedium24dp)
            )
        }

        // Middle Card Section: "Accedi subito"
        MbCardView(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = mbGrayLightExtraBlueDarkColor()
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = Dimen.paddingMedium24dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Accedi subito",
                    style = TextStyle(
                        color = mbMustardDarkWhiteColor(),
                        fontSize = 22.sp,
                        fontFamily = MbYantramanavBoldFontFamily,
                        fontWeight = FontWeight.Bold
                    )
                )

                Text(
                    text = "Sincronizza i tuoi segnalibri su tutti i dispositivi tramite il tuo account Google o Apple.",
                    style = TextStyle(
                        color = if (isSystemInDarkTheme()) Color(0xFFB0B0A8) else Color(0xFF555555),
                        fontSize = 13.sp,
                        fontFamily = MbYantramanavRegularFontFamily
                    )
                )

                // Continua con Google Button
                MbPrimaryButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = mbButtonWhiteLightDarkColor(),
                    text = "Continua con Google",
                ) {
                    onGoogleLogin()
                }

                // Continua con Apple Button
                MbPrimaryButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = mbButtonBlackLightDarkColor(),
                    textColor = MbColor.White,
                    text = "Continua con Apple",
                ) {
                    onAppleLoginClick()
                }
            }
        }

        // Guest / Local Section
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "oppure come Locale",
                style = TextStyle(
                    color = mbMustardDarkWhiteColor(),
                    fontSize = 16.sp,
                    fontFamily = MbYantramanavBoldFontFamily,
                    fontWeight = FontWeight.Bold
                )
            )

            Text(
                text = "I tuoi segnalibri verranno salvati solo in locale su questo dispositivo. Potrai sincronizzarli in qualsiasi momento.",
                style = TextStyle(
                    color = if (isSystemInDarkTheme()) Color(0xFFB0B0A8) else Color(0xFF443300),
                    fontSize = 13.sp,
                    fontFamily = MbYantramanavRegularFontFamily,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.padding(horizontal = 20.dp)
            )

            MbPrimaryButton(
                modifier = Modifier,
                text = "Utente Locale",
                hasMaxWidthEnable = false,
                textColor = MbColor.DarkGray,
                colors = mbButtonWhiteYellowDarkColor()
            ) {
                onGuestLoginClick()
            }
        }

        // Footer Section: Terms & Privacy
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Continuando accetti i ",
                style = TextStyle(
                    color = if (isSystemInDarkTheme()) Color(0xFFB0B0A8) else Color(0xFF443300),
                    fontSize = 12.sp,
                    fontFamily = MbYantramanavRegularFontFamily
                )
            )
            Text(
                text = "Termini di Servizio",
                style = TextStyle(
                    color = if (isSystemInDarkTheme()) MbColor.Yellow else Color(0xFF161612),
                    fontSize = 12.sp,
                    fontFamily = MbYantramanavBoldFontFamily,
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.Underline
                ),
                modifier = Modifier.clickable { onTermsClick() }
            )
            Text(
                text = " e l'",
                style = TextStyle(
                    color = if (isSystemInDarkTheme()) Color(0xFFB0B0A8) else Color(0xFF443300),
                    fontSize = 12.sp,
                    fontFamily = MbYantramanavRegularFontFamily
                )
            )
            Text(
                text = "Informativa sulla Privacy",
                style = TextStyle(
                    color = if (isSystemInDarkTheme()) MbColor.Yellow else Color(0xFF161612),
                    fontSize = 12.sp,
                    fontFamily = MbYantramanavBoldFontFamily,
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.Underline
                ),
                modifier = Modifier.clickable { onPrivacyClick() }
            )
        }
    }
}

@Composable
fun GoogleLogoIcon(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.size(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = size.width * 0.22f

            // Blue arc (right)
            drawArc(
                color = Color(0xFF4285F4),
                startAngle = -45f,
                sweepAngle = 90f,
                useCenter = false,
                style = Stroke(width = strokeWidth)
            )
            // Green arc (bottom)
            drawArc(
                color = Color(0xFF34A853),
                startAngle = 45f,
                sweepAngle = 90f,
                useCenter = false,
                style = Stroke(width = strokeWidth)
            )
            // Yellow arc (left)
            drawArc(
                color = Color(0xFFFBBC05),
                startAngle = 135f,
                sweepAngle = 90f,
                useCenter = false,
                style = Stroke(width = strokeWidth)
            )
            // Red arc (top)
            drawArc(
                color = Color(0xFFEA4335),
                startAngle = 225f,
                sweepAngle = 90f,
                useCenter = false,
                style = Stroke(width = strokeWidth)
            )
        }
        Text(
            text = "G",
            style = TextStyle(
                color = Color(0xFF4285F4),
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
@Preview(name = "Light Mode", showBackground = true, showSystemUi = true)
@Preview(name = "Dark Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun LoginViewPreview() {
    MaterialBookmarkMaterialTheme {
        LoginView()
    }
}
