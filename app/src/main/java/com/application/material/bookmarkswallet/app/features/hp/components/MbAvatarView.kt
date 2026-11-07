package com.application.material.bookmarkswallet.app.features.hp.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.core.graphics.drawable.toBitmap
import coil.imageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.application.material.bookmarkswallet.app.R
import com.application.material.bookmarkswallet.app.features.bookmarkList.model.User
import com.application.material.bookmarkswallet.app.ui.style.Dimen
import com.application.material.bookmarkswallet.app.ui.style.mbWhiteYellowLemonDarkLightColor
import com.application.material.bookmarkswallet.app.utils.ZERO_EIGHTF

@Composable
fun MbAvatarView(
    modifier: Modifier,
    avatarSize: Dp = Dimen.size72dp,
    user: User? = null
) {
    val imageBitmap = rememberImageBitmapFromUrl(
        url = user?.photoUrl
    )

    Box(
        modifier = modifier
            .size(size = avatarSize)
            .clip(CircleShape)
            .background(color = mbWhiteYellowLemonDarkLightColor())
            .padding(Dimen.paddingExtraSmall4dp),
        contentAlignment = Alignment.Center
    ) {
        when {
            imageBitmap != null ->
                Image(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape),
                    bitmap = imageBitmap,
                    contentDescription = "Profile",
                    contentScale = ContentScale.Crop
                )

            else ->
                Image(
                    painter = painterResource(id = R.drawable.ic_fox_illustration),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize(fraction = ZERO_EIGHTF)
                        .clip(CircleShape)
                )

//            Icon(
//                    modifier = Modifier
//                        .padding(
//                            Dimen.padding32dp
//                        )
//                        .size(
//                            avatarSize
//                        ),
//                    imageVector = ImageVector.vectorResource(R.drawable.ic_navbar_user),
//                    contentDescription = "Profile",
//                    tint = azHeavyWhiteGreenMintColor()
//                )
        }
    }
}


@Composable
fun rememberImageBitmapFromUrl(url: String?): ImageBitmap? {
    val context = LocalContext.current
    var imageBitmap by remember(url) { mutableStateOf<ImageBitmap?>(null) }

    LaunchedEffect(url) {
        val request = ImageRequest.Builder(context)
            .data(url)
            .build()

        val result = context.imageLoader.execute(request)
        if (result is SuccessResult) {
            val drawable = result.drawable
            val bitmap: Bitmap = drawable.toBitmap()
            imageBitmap = bitmap.asImageBitmap()
        }
    }

    return imageBitmap
}