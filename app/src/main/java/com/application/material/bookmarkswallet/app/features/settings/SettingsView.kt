package com.application.material.bookmarkswallet.app.features.settings

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.application.material.bookmarkswallet.app.R
import com.application.material.bookmarkswallet.app.features.bookmarkList.components.UserLoginCardView
import com.application.material.bookmarkswallet.app.features.bookmarkList.model.User
import com.application.material.bookmarkswallet.app.features.settings.viewmodels.SettingsViewModel
import com.application.material.bookmarkswallet.app.ui.MaterialBookmarkMaterialTheme
import com.application.material.bookmarkswallet.app.ui.components.MbCardView
import com.application.material.bookmarkswallet.app.ui.components.MbPrimaryButton
import com.application.material.bookmarkswallet.app.ui.style.Dimen
import com.application.material.bookmarkswallet.app.ui.style.mbBasicCardBackgroundColors
import com.application.material.bookmarkswallet.app.ui.style.mbButtonTextStyle
import com.application.material.bookmarkswallet.app.ui.style.mbGrayLightColor2
import com.application.material.bookmarkswallet.app.ui.style.mbGrayLightExtraBlueDarkColor
import com.application.material.bookmarkswallet.app.ui.style.mbMustardDarkWhiteColor
import com.application.material.bookmarkswallet.app.ui.style.mbSettingsProFeatCardBackgroundColor
import com.application.material.bookmarkswallet.app.ui.style.mbSubtitleTextSmallStyle
import com.application.material.bookmarkswallet.app.ui.style.mbSubtitleTextStyle
import com.application.material.bookmarkswallet.app.ui.style.mbSwitchColors
import com.application.material.bookmarkswallet.app.ui.style.mbTitleHExtraBigBoldYellowTextStyle
import com.application.material.bookmarkswallet.app.ui.style.mbTitleMediumBoldYellowLightDarkTextStyle
import com.application.material.bookmarkswallet.app.ui.style.mbYellowLemonAlternateColor
import com.application.material.bookmarkswallet.app.ui.style.mbYellowLemonLightColor
import com.application.material.bookmarkswallet.app.utils.EMPTY
import com.application.material.bookmarkswallet.app.utils.ONE
import com.application.material.bookmarkswallet.app.utils.TWO

@Composable
fun SettingsView(
    modifier: Modifier = Modifier,
    settingsViewModel: SettingsViewModel = hiltViewModel<SettingsViewModel>()
) {
    val user by remember {
        mutableStateOf(
            value = settingsViewModel.user
        )
    }

    SettingsInternalView(
        modifier = modifier,
        user = user
    )
}

@Composable
fun SettingsInternalView(
    modifier: Modifier = Modifier,
    user: User,
) {
    Column(
        modifier = modifier
            .verticalScroll(state = rememberScrollState())
            .padding(all = Dimen.paddingMedium16dp),
        verticalArrangement = Arrangement.spacedBy(Dimen.paddingMedium16dp)
    ) {
        Text(
            modifier = modifier
                .padding(
                    top = Dimen.paddingSmall8dp,
                    bottom = Dimen.paddingMedium16dp
                ),
            style = mbTitleHExtraBigBoldYellowTextStyle(),
            text = stringResource(R.string.settings),
        )

        settingsComponentItems
            .onEach { setting ->
                when (setting.type) {
                    SettingsItemType.SETTING_USER -> UserLoginCardView(
                        modifier = modifier
                            .fillMaxWidth(),
                        user = user,
                    )

                    else -> SettingItemCardView(
                        modifier = modifier
                            .fillMaxWidth(),
                        setting = setting
                    )
                }
            }

        //title
        Text(
            modifier = modifier
                .align(
                    alignment = Alignment.Start
                )
                .padding(bottom = Dimen.paddingSmall8dp),
            style = mbSubtitleTextStyle().copy(
                fontSize = Dimen.fontSizeMedium16sp
            ),
            text = "Something more here...\n" +
                    "Thanks for illustration in app due to a dedicated work."
        )

        Image(
            modifier = modifier
                .size(200.dp)
                .padding(vertical = Dimen.paddingMedium16dp)
                .align(alignment = Alignment.CenterHorizontally),
            painter = painterResource(id = R.drawable.ic_fox_illustration_200),
            contentDescription = EMPTY
        )
    }
}

@Composable
fun SettingItemCardView(
    modifier: Modifier = Modifier,
    setting: SettingsItem
) {
    val localUriHandler = LocalUriHandler.current

    val isChecked = remember {
        mutableStateOf(true)
    }
    MbCardView(
        modifier = modifier
            .fillMaxWidth(),
        colors = when {
            setting.isProFeat -> mbSettingsProFeatCardBackgroundColor()

            else -> mbBasicCardBackgroundColors()
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = Dimen.paddingMedium16dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                //image icon
                setting.iconRes?.let {
                    Image(
                        modifier = Modifier
                            .padding(end = Dimen.paddingMedium16dp)
                            .size(size = Dimen.size48dp),
                        painter = painterResource(id = it),
                        contentDescription = EMPTY
                    )
                }

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(Dimen.paddingSmall8dp)
                ) {
                    // Header Row: Title + PRO label
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        setting.nameRes?.let {
                            Text(
                                modifier = Modifier.weight(1f, fill = false),
                                style = mbTitleMediumBoldYellowLightDarkTextStyle(
                                    isHighlighted = setting.isProFeat
                                ).copy(
                                    fontSize = Dimen.fontSizeMedium20sp
                                ),
                                maxLines = 2,
                                text = stringResource(id = it)
                            )
                        }

                        if (setting.isProFeat) {
                            Text(
                                modifier = Modifier.padding(start = Dimen.paddingSmall8dp),
                                style = mbSubtitleTextSmallStyle(
                                    color = mbGrayLightExtraBlueDarkColor()
                                ),
                                maxLines = ONE,
                                text = stringResource(id = R.string.pro_label)
                            )
                        }
                    }

                    // Description
                    setting.descriptionRes?.let {
                        Text(
                            style = mbSubtitleTextStyle().copy(
                                fontSize = Dimen.fontSizeMedium16sp
                            ),
                            text = stringResource(id = it)
                        )
                    }

                    // Children items
                    setting.childrenItem?.let { children ->
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(Dimen.paddingSmall8dp)
                        ) {
                            children.forEach { child ->
                                when (child.childrenItemType) {
                                    SettingsChildrenItemType.SETTING_CHILD_SWITCH -> {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                modifier = Modifier.weight(1f),
                                                style = mbSubtitleTextStyle(
                                                    color = when {
                                                        setting.isProFeat -> mbYellowLemonAlternateColor()
                                                        else -> mbMustardDarkWhiteColor()
                                                    }
                                                ).copy(
                                                    fontSize = Dimen.fontSizeMedium16sp
                                                ),
                                                maxLines = TWO,
                                                text = stringResource(id = (child as ChildrenSwitchItem).labelRes)
                                            )
                                            Switch(
                                                checked = isChecked.value,
                                                colors = mbSwitchColors(),
                                                onCheckedChange = { isChecked.value = it }
                                            )
                                        }
                                    }

                                    SettingsChildrenItemType.SETTING_CHILD_COLOR -> {
                                        LazyRow(
                                            modifier = Modifier.fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(
                                                space = Dimen.paddingSmall8dp
                                            )
                                        ) {
                                            items(items = (child as ChildrenColorItem).colorList) { color ->
                                                Box(
                                                    modifier = Modifier
                                                        .clip(shape = CircleShape)
                                                        .size(size = Dimen.size48dp)
                                                        .background(color = color)
                                                )
                                            }
                                        }
                                    }

                                    else -> {}
                                }
                            }
                        }
                    }
                }
            }

            // CTA Button
            setting.ctaLabel?.let {
                MbPrimaryButton(
                    modifier = Modifier.padding(top = Dimen.paddingMedium16dp),
                    horizontalPadding = Dimen.paddingMedium16dp,
                    textStyle = mbButtonTextStyle(),
                    text = stringResource(id = it),
                    onClickAction = {
                        when {
                            setting.url != null -> localUriHandler.openUri(setting.url)
                            else -> setting.ctaAction?.invoke()
                        }
                    }
                )
            }
        }
    }
}

@Composable
@Preview
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun SettingItemCardView2Preview() {
    MaterialBookmarkMaterialTheme {
        Box(modifier = Modifier.background(mbGrayLightColor2())) {
            SettingsInternalView(
                modifier = Modifier,
                user = User(
                    name = "balalla",
                    email = "baaa",
                    photoUrl = "",
                    uid = ""
                )
            )
        }
    }
}

@Composable
@Preview
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun SettingItemCardViewPreview() {
    MaterialBookmarkMaterialTheme {
        Box(
            modifier = Modifier
                .background(mbYellowLemonLightColor())
                .padding(all = Dimen.paddingMedium16dp)
        ) {
            SettingItemCardView(
                modifier = Modifier,
                setting = SettingsItem(
                    type = SettingsItemType.SETTING_INAPP_PAY_FEAT,
                    nameRes = R.string.in_app_pay_feat_label,
                    childrenItem = listOf(
                    )
                ),
            )
        }
    }
}
