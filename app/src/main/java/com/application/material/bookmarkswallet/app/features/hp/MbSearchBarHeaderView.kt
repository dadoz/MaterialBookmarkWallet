package com.application.material.bookmarkswallet.app.features.hp

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AppBarWithSearch
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.SearchBarColors
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SearchBarDefaults.inputFieldColors
import androidx.compose.material3.SearchBarState
import androidx.compose.material3.SearchBarValue
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberSearchBarState
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.tooling.preview.Preview
import com.application.material.bookmarkswallet.app.features.hp.components.MbAvatarView
import com.application.material.bookmarkswallet.app.ui.MaterialBookmarkMaterialTheme
import com.application.material.bookmarkswallet.app.ui.style.Dimen
import com.application.material.bookmarkswallet.app.ui.style.MbColor
import com.application.material.bookmarkswallet.app.ui.style.mbGrayLightExtraBlueDarkColor
import com.application.material.bookmarkswallet.app.ui.style.mbMustardDarkExtraLightGrayColor
import com.application.material.bookmarkswallet.app.ui.style.mbMustardGrayBlueLightDarkColor
import com.application.material.bookmarkswallet.app.ui.style.mbSubtitleTextStyle
import com.application.material.bookmarkswallet.app.utils.ZEROF
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MbSearchBarHeaderView(
    modifier: Modifier = Modifier,
    textFieldState: TextFieldState,
    searchBarState: SearchBarState,
    onSearchCallback: (String) -> Unit,
    onClearCallback: () -> Unit,
    appBarContainerColor: Color = Color.Transparent
) {
    val scope = rememberCoroutineScope()

    val inputField =
        @Composable {
            SearchBarDefaults.InputField(
                modifier = Modifier,
                searchBarState = searchBarState,
                textFieldState = textFieldState,
                colors = inputFieldColors(
                    focusedTextColor = mbMustardDarkExtraLightGrayColor(),
                    unfocusedTextColor = mbMustardDarkExtraLightGrayColor(),
                    cursorColor = mbMustardDarkExtraLightGrayColor()
                ),
                onSearch = {
                    Timber.e("onSearch $it")
                    onSearchCallback(it)
                },
                placeholder = {
                    if (searchBarState.currentValue == SearchBarValue.Collapsed) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = "Search",
                            style = mbSubtitleTextStyle()
                        )
                    }
                },
                leadingIcon = {
                    when (searchBarState.currentValue) {
                        SearchBarValue.Expanded ->
                            TooltipBox(
                                positionProvider =
                                    TooltipDefaults.rememberTooltipPositionProvider(
                                        TooltipAnchorPosition.Above
                                    ),
                                tooltip = {
                                    PlainTooltip {
                                        Text(
                                            text = "Back"
                                        )
                                    }
                                },
                                state = rememberTooltipState(),
                            ) {
                                IconButton(
                                    onClick = {
                                        textFieldState.clearText()
                                        onClearCallback.invoke()
                                        scope.launch {
                                            searchBarState.animateToCollapsed()
                                        }
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                        contentDescription = "Back",
                                        tint = mbMustardDarkExtraLightGrayColor()
                                    )
                                }
                            }

                        else ->
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null,
                                tint = mbMustardDarkExtraLightGrayColor()
                            )
                    }
                },
                trailingIcon = {
                    when (searchBarState.currentValue) {
                        SearchBarValue.Expanded ->
                            TooltipBox(
                                positionProvider =
                                    TooltipDefaults.rememberTooltipPositionProvider(
                                        positioning = TooltipAnchorPosition.Above
                                    ),
                                tooltip = {
                                    PlainTooltip {
                                        Text(
                                            text = "Clear All"
                                        )
                                    }
                                },
                                state = rememberTooltipState(),
                            ) {
                                IconButton(
                                    onClick = {
                                        textFieldState.clearText()
                                        onClearCallback.invoke()
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Clear All",
                                        tint = mbMustardDarkExtraLightGrayColor()
                                    )
                                }
                            }

                        else -> {}
                    }
                }
            )
        }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .semantics { isTraversalGroup = true },
        verticalAlignment = Alignment.CenterVertically
    ) {
        AppBarWithSearch(
            modifier = Modifier
                .weight(3f)
                .semantics {
                    traversalIndex = ZEROF
                },
            windowInsets = WindowInsets(
                top = Dimen.paddingMedium16dp
            ),
            colors = SearchBarDefaults.appBarWithSearchColors(
                searchBarColors = SearchBarColors(
                    containerColor = mbGrayLightExtraBlueDarkColor(),
                    dividerColor = MbColor.White,
                    inputFieldColors = inputFieldColors(),
                ),
                appBarContainerColor = appBarContainerColor
            ),
            state = searchBarState,
            inputField = inputField
        )

        AnimatedVisibility(
            visible = searchBarState.currentValue != SearchBarValue.Expanded,
        ) {
            MbAvatarView(
                modifier = Modifier
                    .weight(1f)
                    .padding(
                        start = Dimen.paddingMedium16dp,
                        top = Dimen.paddingMedium16dp
                    ),
                avatarSize = Dimen.size64dp
            )
        }
    }
}

@Composable
@Preview
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun BookmarkListViewPreview2Mb() {
    MaterialBookmarkMaterialTheme {
        Column(
            modifier = Modifier
                .background(color = mbMustardGrayBlueLightDarkColor())
                .padding(
                    all = Dimen.paddingMedium16dp
                ),
            verticalArrangement = Arrangement.spacedBy(
                space = Dimen.paddingMedium16dp
            )
        ) {
            MbSearchBarHeaderView(
                modifier = Modifier,
                searchBarState = rememberSearchBarState(
                    initialValue = SearchBarValue.Collapsed
                ),
                textFieldState = rememberTextFieldState(),
                onSearchCallback = {},
                onClearCallback = {},
            )

            MbSearchBarHeaderView(
                modifier = Modifier,
                searchBarState = rememberSearchBarState(
                    initialValue = SearchBarValue.Expanded
                ),
                textFieldState = rememberTextFieldState(),
                onSearchCallback = {},
                onClearCallback = { },
            )
        }
    }
}
