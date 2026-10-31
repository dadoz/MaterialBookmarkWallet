package com.application.material.bookmarkswallet.app.storage

import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey

internal const val MATERIAL_BOOKMARK_STORE_PREFERENCES = "MATERIAL_BOOKMARK_STORE_PREFERENCES"
internal val SELECTED_FILTER_LIST_TYPE = stringPreferencesKey("SELECTED_FILTER_LIST_TYPE")
internal val SELECTED_FILTER_HP_MAP_TYPE = stringSetPreferencesKey("SELECTED_FILTER_HP_MAP_TYPE")

val KEY_USER_ID = stringPreferencesKey("user_id")
val KEY_EMAIL = stringPreferencesKey("user_email")
val KEY_DISPLAY_NAME = stringPreferencesKey("user_display_name")
val KEY_FIRST_NAME = stringPreferencesKey("user_first_name")
val KEY_LAST_NAME = stringPreferencesKey("user_last_name")
val KEY_PHOTO_URL = stringPreferencesKey("user_photo_url")
val KEY_ID_TOKEN = stringPreferencesKey("user_id_token")
val KEY_ID_CITY = stringPreferencesKey("user_id_city")
val KEY_ID_BIO = stringPreferencesKey("user_id_bio")