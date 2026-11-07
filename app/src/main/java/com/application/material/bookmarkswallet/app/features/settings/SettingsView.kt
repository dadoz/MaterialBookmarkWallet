package com.application.material.bookmarkswallet.app.features.settings

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.application.material.bookmarkswallet.app.R
import com.application.material.bookmarkswallet.app.features.bookmarkList.model.User
import com.application.material.bookmarkswallet.app.features.hp.components.MbAvatarView
import com.application.material.bookmarkswallet.app.features.settings.viewmodels.SettingsViewModel
import com.application.material.bookmarkswallet.app.ui.MaterialBookmarkMaterialTheme
import com.application.material.bookmarkswallet.app.ui.components.MbCardView
import com.application.material.bookmarkswallet.app.ui.components.MbPrimaryButton
import com.application.material.bookmarkswallet.app.ui.style.Dimen
import com.application.material.bookmarkswallet.app.ui.style.MbColor
import com.application.material.bookmarkswallet.app.ui.style.MbYantramanavBoldFontFamily
import com.application.material.bookmarkswallet.app.ui.style.MbYantramanavRegularFontFamily
import com.application.material.bookmarkswallet.app.ui.style.homeBackgroundColor
import com.application.material.bookmarkswallet.app.ui.style.mbBasicCardBackgroundColors
import com.application.material.bookmarkswallet.app.ui.style.mbButtonTextStyle
import com.application.material.bookmarkswallet.app.ui.style.mbGrayLightExtraBlueDarkColor
import com.application.material.bookmarkswallet.app.ui.style.mbMustardDarkWhiteColor
import com.application.material.bookmarkswallet.app.ui.style.mbSettingsProFeatCardBackgroundColor
import com.application.material.bookmarkswallet.app.ui.style.mbSubtitleTextSmallStyle
import com.application.material.bookmarkswallet.app.ui.style.mbSubtitleTextStyle
import com.application.material.bookmarkswallet.app.ui.style.mbSwitchColors
import com.application.material.bookmarkswallet.app.ui.style.mbTitleHExtraBigBoldYellowTextStyle
import com.application.material.bookmarkswallet.app.ui.style.mbTitleMediumBoldYellowLightDarkTextStyle
import com.application.material.bookmarkswallet.app.ui.style.mbYellowLemonAlternateColor
import com.application.material.bookmarkswallet.app.ui.style.settingsAvatarColorList
import com.application.material.bookmarkswallet.app.utils.EMPTY
import com.application.material.bookmarkswallet.app.utils.ONE
import com.application.material.bookmarkswallet.app.utils.TWO

val appIcons = listOf(
    Triple("Default", MbColor.Yellow, R.drawable.ic_fox_illustration),
    Triple("Noir Fox", Color(0xFF262626), R.drawable.ic_fox2_illustration),
    Triple("Sunset", Color(0xFFFF7A59), R.drawable.ic_fox_final_illustration),
    Triple("Hacker", Color(0xFF00BFA5), R.drawable.ic_leaf_dark)
)

@Composable
fun SettingsView(
    modifier: Modifier = Modifier,
    settingsViewModel: SettingsViewModel = hiltViewModel<SettingsViewModel>()
) {
    val user by remember {
        mutableStateOf(value = settingsViewModel.user)
    }

    SettingsInternalView(
        modifier = modifier, user = user
    )
}

@Composable
fun SettingsInternalView(
    modifier: Modifier = Modifier,
    user: User,
) {
    var selectedTab by remember { mutableIntStateOf(0) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(homeBackgroundColor())
            .verticalScroll(state = rememberScrollState())
            .padding(bottom = Dimen.paddingExtraLarge64dp)
    ) {
        // Top Header
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = Dimen.paddingMedium16dp,
                    vertical = Dimen.paddingMedium16dp
                ),
            text = stringResource(R.string.settings),
            style = mbTitleHExtraBigBoldYellowTextStyle(),
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimen.paddingMedium16dp),
            verticalArrangement = Arrangement.spacedBy(Dimen.paddingMedium16dp)
        ) {
            // User / Canary VIP Card
            CanaryVipUserCard(
                modifier = Modifier,
                user = user
            )

            // Category Tab Filter Row
            SettingsCategoryTabs(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )

            // Content based on Selected Tab
            when (selectedTab) {
                0 -> { // All Settings
                    DeveloperSupportCardView(modifier = Modifier)
                    SettingsPreferencesCardView(modifier = Modifier)
                    StorageAndDataCardView(modifier = Modifier)
                    PrivacySecurityCardView(modifier = Modifier)
                    SkinColorCardView(modifier = Modifier)
                    AiSearchFeatCardView(modifier = Modifier)
                    SyncGoogleCloudCardView(modifier = Modifier)
                    AppVersionCardView(modifier = Modifier)
                }

                1 -> { // Preferences
                    SettingsPreferencesCardView(modifier = Modifier)
                    SkinColorCardView(modifier = Modifier)
                    AiSearchFeatCardView(modifier = Modifier)
                }

                2 -> { // Data & Sync
                    StorageAndDataCardView(modifier = Modifier)
                    SyncGoogleCloudCardView(modifier = Modifier)
                }

                3 -> { // Security
                    PrivacySecurityCardView(modifier = Modifier)
                    DeveloperSupportCardView(modifier = Modifier)
                    AppVersionCardView(modifier = Modifier)
                }
            }
        }
    }
}

@Composable
fun SettingsCategoryTabs(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val isDark = isSystemInDarkTheme()
    val tabs = listOf("All Settings", "Preferences", "Data & Sync", "Security")

    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(tabs) { index, title ->
            val isSelected = selectedTab == index
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        when {
                            isSelected -> if (isDark) MbColor.Yellow else Color(0xFF161612)
                            else -> if (isDark) Color(0xFF283048) else Color(0xFFFFFFFF)
                        }
                    )
                    .clickable { onTabSelected(index) }
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = title,
                    style = TextStyle(
                        color = when {
                            isSelected -> if (isDark) Color(0xFF161612) else Color.White
                            else -> mbMustardDarkWhiteColor()
                        },
                        fontSize = 14.sp,
                        fontFamily = if (isSelected) MbYantramanavBoldFontFamily else MbYantramanavRegularFontFamily,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                )
            }
        }
    }
}

@Composable
fun SettingsPreferencesCardView(modifier: Modifier = Modifier) {
    var weeklyDigestEnabled by remember { mutableStateOf(true) }
    val isDark = isSystemInDarkTheme()

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Dimen.paddingMedium16dp)
    ) {
        // Section Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Preferences",
                style = TextStyle(
                    color = mbMustardDarkWhiteColor(),
                    fontSize = 18.sp,
                    fontFamily = MbYantramanavBoldFontFamily,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = "3 options",
                style = TextStyle(
                    color = if (isDark) Color(0xFFB0B0A8) else Color(0xFF888888),
                    fontSize = 13.sp,
                    fontFamily = MbYantramanavRegularFontFamily
                )
            )
        }

        MbCardView(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(Dimen.paddingMedium16dp)
            ) {
                // Item 1: Appearance
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(Dimen.paddingMedium16dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(Dimen.sizeMedium12dp))
                                .background(if (isDark) Color(0xFF283048) else Color(0xFFF4F4F2)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_edit_dark),
                                contentDescription = null,
                                tint = mbMustardDarkWhiteColor(),
                                modifier = Modifier.size(28.dp)
                            )
                        }

                        Column {
                            Text(
                                text = "Appearance",
                                style = TextStyle(
                                    color = mbMustardDarkWhiteColor(),
                                    fontSize = 15.sp,
                                    fontFamily = MbYantramanavBoldFontFamily,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Text(
                                text = "Warm Yellow Canvas",
                                style = TextStyle(
                                    color = if (isDark) Color(0xFFB0B0A8) else Color(0xFF666666),
                                    fontSize = 12.sp,
                                    fontFamily = MbYantramanavRegularFontFamily
                                )
                            )
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFFFED100))
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "Warm #FED100",
                                style = TextStyle(
                                    color = Color(0xFF443300),
                                    fontSize = 11.sp,
                                    fontFamily = MbYantramanavBoldFontFamily,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }

                        Text(
                            text = "›",
                            style = TextStyle(
                                color = if (isDark) Color(0xFFB0B0A8) else Color(0xFF888888),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }

                // Item 2: Weekly Digest
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(Dimen.paddingMedium16dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(Dimen.sizeMedium12dp))
                                .background(if (isDark) Color(0xFF283048) else Color(0xFFF4F4F2)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_share_dark),
                                contentDescription = null,
                                tint = mbMustardDarkWhiteColor(),
                                modifier = Modifier.size(28.dp)
                            )
                        }

                        Column {
                            Text(
                                text = "Weekly Digest",
                                style = TextStyle(
                                    color = mbMustardDarkWhiteColor(),
                                    fontSize = 15.sp,
                                    fontFamily = MbYantramanavBoldFontFamily,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Text(
                                text = "Curated bookmarks email",
                                style = TextStyle(
                                    color = if (isDark) Color(0xFFB0B0A8) else Color(0xFF666666),
                                    fontSize = 12.sp,
                                    fontFamily = MbYantramanavRegularFontFamily
                                )
                            )
                        }
                    }

                    Switch(
                        checked = weeklyDigestEnabled,
                        colors = mbSwitchColors(),
                        onCheckedChange = { weeklyDigestEnabled = it }
                    )
                }

                // Item 3: Browser Extension
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(Dimen.paddingMedium16dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(Dimen.sizeMedium12dp))
                                .background(if (isDark) Color(0xFF283048) else Color(0xFFF4F4F2)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_tab_bookmark_dark),
                                contentDescription = null,
                                tint = mbMustardDarkWhiteColor(),
                                modifier = Modifier.size(28.dp)
                            )
                        }

                        Column {
                            Text(
                                text = "Browser Extension",
                                style = TextStyle(
                                    color = mbMustardDarkWhiteColor(),
                                    fontSize = 15.sp,
                                    fontFamily = MbYantramanavBoldFontFamily,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Text(
                                text = "Chrome & Safari 1.4.2",
                                style = TextStyle(
                                    color = if (isDark) Color(0xFFB0B0A8) else Color(0xFF666666),
                                    fontSize = 12.sp,
                                    fontFamily = MbYantramanavRegularFontFamily
                                )
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isDark) Color(0xFF1C3A27) else Color(0xFFE8F8EE))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "• Active",
                            style = TextStyle(
                                color = if (isDark) Color(0xFF0EE081) else Color(0xFF188038),
                                fontSize = 11.sp,
                                fontFamily = MbYantramanavBoldFontFamily,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StorageAndDataCardView(modifier: Modifier = Modifier) {
    val isDark = isSystemInDarkTheme()

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Dimen.paddingMedium16dp)
    ) {
        // Section Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Storage & Data",
                style = TextStyle(
                    color = mbMustardDarkWhiteColor(),
                    fontSize = 18.sp,
                    fontFamily = MbYantramanavBoldFontFamily,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = "Cloud Synced",
                style = TextStyle(
                    color = if (isDark) Color(0xFFB0B0A8) else Color(0xFF888888),
                    fontSize = 13.sp,
                    fontFamily = MbYantramanavRegularFontFamily
                )
            )
        }

        MbCardView(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(Dimen.paddingMedium16dp)
            ) {
                // Row 1: Import & Export
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(Dimen.paddingMedium16dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(Dimen.sizeMedium12dp))
                                .background(if (isDark) Color(0xFF283048) else Color(0xFFF4F4F2)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_share_dark),
                                contentDescription = null,
                                tint = mbMustardDarkWhiteColor(),
                                modifier = Modifier.size(28.dp)
                            )
                        }

                        Column {
                            Text(
                                text = "Import & Export",
                                style = TextStyle(
                                    color = mbMustardDarkWhiteColor(),
                                    fontSize = 15.sp,
                                    fontFamily = MbYantramanavBoldFontFamily,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Text(
                                text = "HTML, Notion, Raindrop, JSON",
                                style = TextStyle(
                                    color = if (isDark) Color(0xFFB0B0A8) else Color(0xFF666666),
                                    fontSize = 12.sp,
                                    fontFamily = MbYantramanavRegularFontFamily
                                )
                            )
                        }
                    }

                    Text(
                        text = "›",
                        style = TextStyle(
                            color = if (isDark) Color(0xFFB0B0A8) else Color(0xFF888888),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                // Row 2: Offline Reading Cache
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(Dimen.paddingMedium16dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(Dimen.sizeMedium12dp))
                                .background(if (isDark) Color(0xFF283048) else Color(0xFFF4F4F2)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_text_dark),
                                contentDescription = null,
                                tint = mbMustardDarkWhiteColor(),
                                modifier = Modifier.size(28.dp)
                            )
                        }

                        Column {
                            Text(
                                text = "Offline Reading Cache",
                                style = TextStyle(
                                    color = mbMustardDarkWhiteColor(),
                                    fontSize = 15.sp,
                                    fontFamily = MbYantramanavBoldFontFamily,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Text(
                                text = "42.8 MB cached pages",
                                style = TextStyle(
                                    color = if (isDark) Color(0xFFB0B0A8) else Color(0xFF666666),
                                    fontSize = 12.sp,
                                    fontFamily = MbYantramanavRegularFontFamily
                                )
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(if (isDark) Color(0xFF283048) else Color(0xFFF4F4F2))
                            .clickable { }
                            .padding(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "Clear",
                            style = TextStyle(
                                color = mbMustardDarkWhiteColor(),
                                fontSize = 12.sp,
                                fontFamily = MbYantramanavBoldFontFamily,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PrivacySecurityCardView(modifier: Modifier = Modifier) {
    val isDark = isSystemInDarkTheme()

    MbCardView(
        modifier = modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Dimen.paddingMedium16dp),
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(Dimen.sizeMedium12dp))
                        .background(if (isDark) Color(0xFF283048) else Color(0xFFF4F4F2)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_pin),
                        contentDescription = null,
                        tint = mbMustardDarkWhiteColor(),
                        modifier = Modifier.size(28.dp)
                    )
                }

                Column {
                    Text(
                        text = "Privacy & Security",
                        style = TextStyle(
                            color = mbMustardDarkWhiteColor(),
                            fontSize = 15.sp,
                            fontFamily = MbYantramanavBoldFontFamily,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = "Biometric lock enabled",
                        style = TextStyle(
                            color = if (isDark) Color(0xFFB0B0A8) else Color(0xFF666666),
                            fontSize = 12.sp,
                            fontFamily = MbYantramanavRegularFontFamily
                        )
                    )
                }
            }

            Text(
                text = "›",
                style = TextStyle(
                    color = if (isDark) Color(0xFFB0B0A8) else Color(0xFF888888),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}

@Composable
fun CanaryVipUserCard(
    user: User?,
    modifier: Modifier = Modifier,
    onEditProfileClick: (() -> Unit)? = null,
    onShareClick: (() -> Unit)? = null
) {
    val isDark = isSystemInDarkTheme()

    MbCardView(
        modifier = modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(Dimen.paddingMedium16dp)
        ) {
            // Top Section: Avatar (left) and PRO Badge + Joined Date (right)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                // Avatar with Yellow Ring and Pencil Edit Badge
                Box(
                    modifier = Modifier.size(76.dp)
                ) {

                    MbAvatarView(
                        modifier = Modifier.size(76.dp),
                        user = user
                    )

                    // Edit Badge (pencil)
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .size(26.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF222222))
                            .padding(5.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_edit_dark),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(12.dp)
                        )
                    }
                }

                // Badge PRO MEMBER & Joined Date
                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(MbColor.Yellow)
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_star),
                                contentDescription = null,
                                tint = Color(0xFF161612),
                                modifier = Modifier.size(14.dp)
                            )
                            Text(
                                text = "PRO MEMBER",
                                style = TextStyle(
                                    color = Color(0xFF161612),
                                    fontSize = 11.sp,
                                    fontFamily = MbYantramanavBoldFontFamily,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }

                    Text(
                        text = "Joined Aug 2023",
                        style = TextStyle(
                            color = if (isDark) Color(0xFFB0B0A8) else Color(0xFF888888),
                            fontSize = 12.sp,
                            fontFamily = MbYantramanavRegularFontFamily
                        )
                    )
                }
            }

            // User Name & Role / Handle
            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = user?.name?.takeIf { it.isNotEmpty() } ?: "Alex Rivera",
                    style = TextStyle(
                        color = mbMustardDarkWhiteColor(),
                        fontSize = 24.sp,
                        fontFamily = MbYantramanavBoldFontFamily,
                        fontWeight = FontWeight.Bold
                    )
                )

                Text(
                    text = user?.email?.takeIf { it.isNotEmpty() }
                        ?: "@alexdesign · Product Designer",
                    style = TextStyle(
                        color = if (isDark) Color(0xFFB0B0A8) else Color(0xFF666666),
                        fontSize = 14.sp,
                        fontFamily = MbYantramanavRegularFontFamily
                    )
                )
            }

            // Stats Row (3 Pill containers)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Bookmarks
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(Dimen.paddingMedium24dp))
                        .background(if (isDark) Color(0xFF283048) else Color(0xFFF4F4F2))
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Text(
                            text = "128",
                            style = TextStyle(
                                color = mbMustardDarkWhiteColor(),
                                fontSize = 20.sp,
                                fontFamily = MbYantramanavBoldFontFamily,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            text = stringResource(R.string.bookmarks_title_string),
                            style = TextStyle(
                                color = if (isDark) Color(0xFFB0B0A8) else Color(0xFF666666),
                                fontSize = 12.sp,
                                fontFamily = MbYantramanavRegularFontFamily
                            )
                        )
                    }
                }

                // Collections
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(Dimen.paddingMedium24dp))
                        .background(if (isDark) Color(0xFF283048) else Color(0xFFF4F4F2))
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Text(
                            text = "14",
                            style = TextStyle(
                                color = mbMustardDarkWhiteColor(),
                                fontSize = 20.sp,
                                fontFamily = MbYantramanavBoldFontFamily,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            text = "Collections",
                            style = TextStyle(
                                color = if (isDark) Color(0xFFB0B0A8) else Color(0xFF666666),
                                fontSize = 12.sp,
                                fontFamily = MbYantramanavRegularFontFamily
                            )
                        )
                    }
                }

                // Pinned
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(Dimen.paddingMedium24dp))
                        .background(if (isDark) Color(0xFF283048) else Color(0xFFF4F4F2))
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Text(
                            text = "32",
                            style = TextStyle(
                                color = mbMustardDarkWhiteColor(),
                                fontSize = 20.sp,
                                fontFamily = MbYantramanavBoldFontFamily,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            text = "Pinned",
                            style = TextStyle(
                                color = if (isDark) Color(0xFFB0B0A8) else Color(0xFF666666),
                                fontSize = 12.sp,
                                fontFamily = MbYantramanavRegularFontFamily
                            )
                        )
                    }
                }
            }

            // Action Buttons Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Edit Profile Button
                MbPrimaryButton(
                    modifier = Modifier
                        .weight(1f),
                    textColor = MbColor.DarkGray,
                    text = "Edit Profile"
                ) {
                    onEditProfileClick?.invoke()
                }

                // Share Button
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(if (isDark) Color(0xFF283048) else Color(0xFFF4F4F2))
                        .clickable { onShareClick?.invoke() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_share_dark),
                        contentDescription = null,
                        tint = mbMustardDarkWhiteColor(),
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun SkinColorCardView(modifier: Modifier) {
    var selectedColorIndex by remember { mutableIntStateOf(0) }
    var isAmoledTheme by remember { mutableStateOf(true) }
    var selectedAppIcon by remember { mutableIntStateOf(0) }
    val isDark = isSystemInDarkTheme()

    MbCardView(
        modifier = modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(Dimen.paddingMedium16dp)
        ) {
            // Header
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(Dimen.paddingMedium16dp)
                ) {
                    Box(
                        modifier = modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(Dimen.sizeMedium12dp))
                            .background(if (isDark) Color(0xFF2A2A1E) else Color(0xFFFFF9C4)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_edit_dark),
                            contentDescription = null,
                            tint = if (isDark) MbColor.Yellow else Color(0xFF333333),
                            modifier = modifier.size(28.dp)
                        )
                    }

                    Column {
                        Text(
                            text = stringResource(R.string.skin_color_title),
                            style = TextStyle(
                                color = mbMustardDarkWhiteColor(),
                                fontSize = 18.sp,
                                fontFamily = MbYantramanavBoldFontFamily,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            text = stringResource(R.string.skin_color_subtitle),
                            style = TextStyle(
                                color = if (isDark) Color(0xFFB0B0A8) else Color(0xFF666666),
                                fontSize = 13.sp,
                                fontFamily = MbYantramanavRegularFontFamily
                            )
                        )
                    }
                }

                // PRO Badge
                Box(
                    modifier = modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (isDark) Color(0xFF2A2A1E) else Color(0xFFFFF599))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = stringResource(R.string.pro_label),
                        style = TextStyle(
                            color = if (isDark) MbColor.Yellow else Color(0xFF554400),
                            fontSize = 11.sp,
                            fontFamily = MbYantramanavBoldFontFamily,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }

            // Colore Primario Sistema
            Column(verticalArrangement = Arrangement.spacedBy(Dimen.paddingSmall8dp)) {
                Text(
                    text = stringResource(R.string.primary_color_section),
                    style = TextStyle(
                        color = mbMustardDarkWhiteColor(),
                        fontSize = 14.sp,
                        fontFamily = MbYantramanavBoldFontFamily,
                        fontWeight = FontWeight.Bold
                    )
                )

                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    settingsAvatarColorList
                        .forEachIndexed { index, color ->
                            Box(
                                modifier = modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                                    .background(color)
                                    .clickable { selectedColorIndex = index },
                                contentAlignment = Alignment.Center
                            ) {
                                if (selectedColorIndex == index) {
                                    Text(
                                        text = "✓",
                                        color = if (color == MbColor.Yellow || color == MbColor.White) Color.Black else Color.White,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                }
            }


            // Tema Scuro AMOLED
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = modifier.weight(1f)) {
                    Text(
                        text = stringResource(R.string.amoled_dark_theme),
                        style = TextStyle(
                            color = mbMustardDarkWhiteColor(),
                            fontSize = 15.sp,
                            fontFamily = MbYantramanavBoldFontFamily,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = stringResource(R.string.amoled_dark_theme_desc),
                        style = TextStyle(
                            color = if (isDark) Color(0xFFB0B0A8) else Color(0xFF777777),
                            fontSize = 13.sp,
                            fontFamily = MbYantramanavRegularFontFamily
                        )
                    )
                }

                Switch(
                    checked = isAmoledTheme,
                    colors = mbSwitchColors(),
                    onCheckedChange = { isAmoledTheme = it })
            }


            // Icona Applicazione (Sbloccata)
            Column(verticalArrangement = Arrangement.spacedBy(Dimen.paddingSmall8dp)) {
                Text(
                    text = stringResource(R.string.app_icon_section),
                    style = TextStyle(
                        color = if (isDark) Color(0xFFB0B0A8) else Color(0xFF666666),
                        fontSize = 13.sp,
                        fontFamily = MbYantramanavRegularFontFamily
                    )
                )

                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    appIcons.forEachIndexed { index, (label, color, iconRes) ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = modifier.clickable { selectedAppIcon = index }) {
                            Box(
                                modifier = modifier
                                    .size(52.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(color), contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = iconRes),
                                    contentDescription = label,
                                    modifier = modifier.size(32.dp)
                                )
                            }
                            Text(
                                text = label, style = TextStyle(
                                    color = if (selectedAppIcon == index) mbMustardDarkWhiteColor() else Color.Gray,
                                    fontSize = 12.sp,
                                    fontFamily = if (selectedAppIcon == index) MbYantramanavBoldFontFamily else MbYantramanavRegularFontFamily
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AiSearchFeatCardView(modifier: Modifier) {
    var switch1 by remember { mutableStateOf(true) }
    var switch2 by remember { mutableStateOf(true) }
    var switch3 by remember { mutableStateOf(true) }
    var switch4 by remember { mutableStateOf(true) }
    var switch5 by remember { mutableStateOf(true) }
    val isDark = isSystemInDarkTheme()

    val items = listOf(
        Triple(
            R.string.search_with_ai_label,
            R.string.search_ai_desc,
            switch1 to { v: Boolean -> switch1 = v }),
        Triple(
            R.string.enable_preview_with_ecosia,
            R.string.ecosia_preview_desc,
            switch2 to { v: Boolean -> switch2 = v }),
        Triple(
            R.string.fine_search_with_ai,
            R.string.fine_search_desc,
            switch3 to { v: Boolean -> switch3 = v }),
        Triple(
            R.string.download_bookmark,
            R.string.download_bookmarks_desc,
            switch4 to { v: Boolean -> switch4 = v }),
        Triple(
            R.string.inapp_purchase_config,
            R.string.inapp_config_desc,
            switch5 to { v: Boolean -> switch5 = v })
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Dimen.mbCardRoundCornerSize))
            .background(if (isDark) MbColor.BlueBlackExtraDark else Color(0xFF161612))
            .padding(all = Dimen.paddingMedium16dp)
    ) {
        Column(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(Dimen.paddingMedium16dp)
        ) {
            // Header Row
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(Dimen.paddingMedium16dp)
                ) {
                    Box(
                        modifier = modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(Dimen.sizeMedium12dp))
                            .background(if (isDark) Color(0xFF283048) else Color(0xFF2A2A1E)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_tab_bookmark_dark),
                            contentDescription = null,
                            tint = MbColor.Yellow,
                            modifier = modifier.size(28.dp)
                        )
                    }

                    Column {
                        Text(
                            text = stringResource(R.string.ai_feat_title),
                            style = TextStyle(
                                color = MbColor.White,
                                fontSize = 18.sp,
                                fontFamily = MbYantramanavBoldFontFamily,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            text = stringResource(R.string.ai_feat_subtitle),
                            style = TextStyle(
                                color = Color(0xFFB0B0A8),
                                fontSize = 13.sp,
                                fontFamily = MbYantramanavRegularFontFamily
                            )
                        )
                    }
                }

                Text(
                    text = stringResource(R.string.pro_label),
                    style = TextStyle(
                        color = MbColor.Yellow,
                        fontSize = 14.sp,
                        fontFamily = MbYantramanavBoldFontFamily,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            // List of Switch items
            Column(verticalArrangement = Arrangement.spacedBy(Dimen.paddingMedium16dp)) {
                items.forEach { (titleRes, descRes, statePair) ->
                    Row(
                        modifier = modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = modifier.weight(1f)) {
                            Text(
                                text = stringResource(titleRes),
                                style = TextStyle(
                                    color = MbColor.Yellow,
                                    fontSize = 15.sp,
                                    fontFamily = MbYantramanavBoldFontFamily,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Text(
                                text = stringResource(descRes),
                                style = TextStyle(
                                    color = Color(0xFFA0A098),
                                    fontSize = 12.sp,
                                    fontFamily = MbYantramanavRegularFontFamily
                                )
                            )
                        }

                        Switch(
                            checked = statePair.first,
                            colors = mbSwitchColors(),
                            onCheckedChange = statePair.second
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SyncGoogleCloudCardView(modifier: Modifier) {
    var syncEnabled by remember { mutableStateOf(true) }
    var syncBookmarks by remember { mutableStateOf(true) }
    val isDark = isSystemInDarkTheme()

    MbCardView(
        modifier = modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(Dimen.paddingMedium16dp)
        ) {
            // Header
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(Dimen.paddingMedium16dp)
                ) {
                    Box(
                        modifier = modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(Dimen.sizeMedium12dp))
                            .background(if (isDark) Color(0xFF283048) else Color(0xFFF0F4FF)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_tab_user_dark),
                            contentDescription = null,
                            tint = if (isDark) MbColor.Yellow else Color(0xFF1E243A),
                            modifier = modifier.size(28.dp)
                        )
                    }

                    Column {
                        Text(
                            text = stringResource(R.string.sync_cloud_title),
                            style = TextStyle(
                                color = mbMustardDarkWhiteColor(),
                                fontSize = 18.sp,
                                fontFamily = MbYantramanavBoldFontFamily,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            text = stringResource(R.string.sync_google_subtitle),
                            style = TextStyle(
                                color = if (isDark) Color(0xFFB0B0A8) else Color(0xFF666666),
                                fontSize = 12.sp,
                                fontFamily = MbYantramanavRegularFontFamily
                            )
                        )
                    }
                }

                // ATTIVO Badge (Green)
                Box(
                    modifier = modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (isDark) Color(0xFF1C3A27) else Color(0xFFE8F8EE))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = stringResource(R.string.attivo_badge),
                        style = TextStyle(
                            color = if (isDark) Color(0xFF0EE081) else Color(0xFF188038),
                            fontSize = 11.sp,
                            fontFamily = MbYantramanavBoldFontFamily,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }

            // Switches
            Column(verticalArrangement = Arrangement.spacedBy(Dimen.paddingMedium16dp)) {
                // Sync Google Enabled
                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = modifier.weight(1f)) {
                        Text(
                            text = stringResource(R.string.sync_google_enabled),
                            style = TextStyle(
                                color = mbMustardDarkWhiteColor(),
                                fontSize = 15.sp,
                                fontFamily = MbYantramanavBoldFontFamily,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            text = stringResource(R.string.sync_google_account),
                            style = TextStyle(
                                color = if (isDark) Color(0xFFB0B0A8) else Color(0xFF666666),
                                fontSize = 12.sp,
                                fontFamily = MbYantramanavRegularFontFamily
                            )
                        )
                    }

                    Switch(
                        checked = syncEnabled,
                        colors = mbSwitchColors(),
                        onCheckedChange = { syncEnabled = it })
                }


                // Sync Bookmarks
                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = modifier.weight(1f)) {
                        Text(
                            text = stringResource(R.string.sync_bookmark),
                            style = TextStyle(
                                color = mbMustardDarkWhiteColor(),
                                fontSize = 15.sp,
                                fontFamily = MbYantramanavBoldFontFamily,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            text = stringResource(R.string.sync_bookmarks_count),
                            style = TextStyle(
                                color = if (isDark) Color(0xFFB0B0A8) else Color(0xFF666666),
                                fontSize = 12.sp,
                                fontFamily = MbYantramanavRegularFontFamily
                            )
                        )
                    }

                    Switch(
                        checked = syncBookmarks,
                        colors = mbSwitchColors(),
                        onCheckedChange = { syncBookmarks = it })
                }
            }

            // Action Buttons Row
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Dimen.paddingSmall8dp)
            ) {
                // Esporta HTML/JSON
                Box(
                    modifier = modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(20.dp))
                        .background(if (isDark) Color(0xFF283048) else Color(0xFFF4F4F2))
                        .clickable { }
                        .padding(vertical = 12.dp, horizontal = 8.dp),
                    contentAlignment = Alignment.Center) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_share_dark),
                            contentDescription = null,
                            tint = if (isDark) MbColor.Yellow else Color(0xFF333333),
                            modifier = modifier.size(28.dp)
                        )
                        Text(
                            text = stringResource(R.string.export_html_json),
                            style = TextStyle(
                                color = mbMustardDarkWhiteColor(),
                                fontSize = 12.sp,
                                fontFamily = MbYantramanavBoldFontFamily,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }

                // Ripristina Backup
                Box(
                    modifier = modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(20.dp))
                        .background(if (isDark) Color(0xFF283048) else Color(0xFFF4F4F2))
                        .clickable { }
                        .padding(vertical = 12.dp, horizontal = 8.dp),
                    contentAlignment = Alignment.Center) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_text_dark),
                            contentDescription = null,
                            tint = if (isDark) MbColor.Yellow else Color(0xFF333333),
                            modifier = modifier.size(28.dp)
                        )
                        Text(
                            text = stringResource(R.string.restore_backup),
                            style = TextStyle(
                                color = mbMustardDarkWhiteColor(),
                                fontSize = 12.sp,
                                fontFamily = MbYantramanavBoldFontFamily,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DeveloperSupportCardView(
    modifier: Modifier,
    supportDevAction: () -> Unit = {}
) {
    val isDark = isSystemInDarkTheme()

    MbCardView(
        modifier = modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(Dimen.paddingMedium16dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Dimen.paddingMedium16dp)
            ) {
                Box(
                    modifier = modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(Dimen.sizeMedium12dp))
                        .background(if (isDark) Color(0xFF2A2A1E) else Color(0xFFFFF8E1)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_buymeacoffee_light),
                        contentDescription = null,
                        tint = if (isDark) MbColor.Yellow else MbColor.DarkGray,
                        modifier = modifier.size(28.dp)
                    )
                }

                Column {
                    Text(
                        text = stringResource(R.string.dev_support_title),
                        style = TextStyle(
                            color = mbMustardDarkWhiteColor(),
                            fontSize = 18.sp,
                            fontFamily = MbYantramanavBoldFontFamily,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = stringResource(R.string.dev_support_sub),
                        style = TextStyle(
                            color = if (isDark) Color(0xFFB0B0A8) else Color(0xFF666666),
                            fontSize = 12.sp,
                            fontFamily = MbYantramanavRegularFontFamily
                        )
                    )
                }
            }

            Text(
                text = stringResource(R.string.dev_support_desc),
                style = TextStyle(
                    color = if (isDark) Color(0xFFB0B0A8) else Color(0xFF666666),
                    fontSize = 13.sp,
                    fontFamily = MbYantramanavRegularFontFamily
                )
            )
            // Edit Profile Button
            MbPrimaryButton(
                modifier = Modifier,
                textColor = MbColor.DarkGray,
                text = stringResource(R.string.dev_support_title),
                onClickAction = supportDevAction
            )
        }
    }
}

@Composable
fun AppVersionCardView(modifier: Modifier) {
    val isDark = isSystemInDarkTheme()

    MbCardView(
        modifier = modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(Dimen.paddingSmall8dp)
        ) {
            Text(
                text = stringResource(R.string.app_version_label),
                style = TextStyle(
                    color = mbMustardDarkWhiteColor(),
                    fontSize = 18.sp,
                    fontFamily = MbYantramanavBoldFontFamily,
                    fontWeight = FontWeight.Bold
                )
            )

            Text(
                text = "version 2.2.0 (" + stringResource(R.string.pro_unlocked) + ")\nbuild number 123456789",
                style = TextStyle(
                    color = if (isDark) Color(0xFFB0B0A8) else Color(0xFF666666),
                    fontSize = 13.sp,
                    fontFamily = MbYantramanavRegularFontFamily
                )
            )

            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.license_id),
                    style = TextStyle(
                        color = if (isDark) Color(0xFFB0B0A8) else Color(0xFF888888),
                        fontSize = 12.sp,
                        fontFamily = MbYantramanavRegularFontFamily
                    )
                )

                Text(
                    text = stringResource(R.string.verified),
                    style = TextStyle(
                        color = if (isDark) Color(0xFF0EE081) else Color(0xFF188038),
                        fontSize = 12.sp,
                        fontFamily = MbYantramanavBoldFontFamily,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}

@Composable
fun SettingItemCardView(
    modifier: Modifier = Modifier,
    setting: SettingsItem
) {
    val localUriHandler = LocalUriHandler.current
    var isChecked by remember { mutableStateOf(true) }

    MbCardView(
        modifier = modifier.fillMaxWidth(),
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
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
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
                                )
                                    .copy(
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

                    setting.descriptionRes?.let {
                        Text(
                            style = mbSubtitleTextStyle()
                                .copy(fontSize = Dimen.fontSizeMedium16sp),
                            text = stringResource(id = it)
                        )
                    }

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
                                                )
                                                    .copy(fontSize = Dimen.fontSizeMedium16sp),
                                                maxLines = TWO,
                                                text = stringResource(id = (child as ChildrenSwitchItem).labelRes)
                                            )
                                            Switch(
                                                checked = isChecked,
                                                colors = mbSwitchColors(),
                                                onCheckedChange = { isChecked = it })
                                        }
                                    }

                                    SettingsChildrenItemType.SETTING_CHILD_COLOR -> {
                                        LazyRow(
                                            modifier = Modifier.fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(Dimen.paddingSmall8dp)
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
                    })
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun SettingsInternalViewPreview() {
    MaterialBookmarkMaterialTheme {
        SettingsInternalView(
            modifier = Modifier,
            user = User(
                name = "Davide User",
                email = "dev.alex@canarypocket.app",
                photoUrl = "",
                userId = "123",
                idToken = "idToken",
                lastName = "last",
                firstName = "first",
                displayName = "davide User"
            )
        )
    }
}
