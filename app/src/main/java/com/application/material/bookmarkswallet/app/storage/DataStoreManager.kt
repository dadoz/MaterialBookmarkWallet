package com.application.material.bookmarkswallet.app.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.application.material.bookmarkswallet.app.features.bookmarkList.model.BookmarkListType
import com.application.material.bookmarkswallet.app.features.bookmarkList.model.FilterHp
import com.application.material.bookmarkswallet.app.features.bookmarkList.model.User
import com.firebase.ui.auth.configuration.auth_provider.AuthProvider.Email.Companion.KEY_EMAIL
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

/**
 * Documentation of DataStore retrieved from
 * https://developer.android.com/topic/libraries/architecture/datastore
 * now we dont use anymore sharedpref but datastore
 */
@Singleton
class DataStoreManager @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

//    private val dataStorePreferences: DataStore<Preferences> by preferencesDataStore(
//        name = MATERIAL_BOOKMARK_STORE_PREFERENCES,
//        scope = CoroutineScope(
//            context = Dispatchers.Default
//        )
//    )

    suspend fun saveUser(user: User) {
        dataStore.edit { preferences ->
            preferences[KEY_USER_ID] = user.userId
            preferences[KEY_EMAIL] = user.email
            preferences[KEY_DISPLAY_NAME] = user.displayName
            preferences[KEY_FIRST_NAME] = user.firstName
            preferences[KEY_LAST_NAME] = user.lastName
            preferences[KEY_PHOTO_URL] = user.photoUrl ?: ""
            preferences[KEY_ID_TOKEN] = user.idToken
        }
        Timber.e(user.toString())
    }

    val userFlow: Flow<User?> = dataStore.data
        .map { preferences ->
            val email = preferences[KEY_EMAIL]
            if (email.isNullOrEmpty()) {
                null
            } else {
                User(
                    userId = preferences[KEY_USER_ID] ?: UUID.randomUUID().toString(),
                    email = email,
                    name = "-",
                    displayName = preferences[KEY_DISPLAY_NAME] ?: "-",
                    firstName = preferences[KEY_FIRST_NAME] ?: "-",
                    lastName = preferences[KEY_LAST_NAME] ?: "-",
                    photoUrl = preferences[KEY_PHOTO_URL],
                    idToken = preferences[KEY_ID_TOKEN] ?: "",
                )
            }
        }

    var selectedFilterListType: Flow<List<BookmarkListType>?>? = null
        get() = dataStore.data
            .map { preferences ->
                preferences[SELECTED_FILTER_LIST_TYPE]
                    ?.let {
                        listOf(
                            BookmarkListType.valueOf(it)
                        )
                    }
            }
        private set

    fun setSelectedFilterListType(
        value: String?,
        coroutineContext: CoroutineContext = Dispatchers.Main
    ) {
        value?.let {
            CoroutineScope(
                context = coroutineContext
            )
                .launch {
                    dataStore
                        .edit { preferences ->
                            preferences[SELECTED_FILTER_LIST_TYPE] = value
                        }
                }
        }
    }

    var selectedFilterHpMap: Flow<Map<FilterHp, Boolean>?>? = null
        get() = dataStore.data
            .map { preferences ->
                preferences[SELECTED_FILTER_HP_MAP_TYPE]
                    ?.associate {
                        it.split(":")
                            .let { item ->
                                (FilterHp.entries.find { it.name == item[0] }
                                    ?: FilterHp.entries[0]) to item[1].toBoolean()
                            }
                    }
            }
        private set

    fun setSelectedFilterHpMap(
        filterHpMap: Map<FilterHp, Boolean>,
        coroutineContext: CoroutineContext = Dispatchers.Main
    ) {
        CoroutineScope(
            context = coroutineContext
        )
            .launch {
                dataStore.edit { preferences ->
                    preferences[SELECTED_FILTER_HP_MAP_TYPE] = filterHpMap
                        .map {
                            it.key.name + ":" + it.value
                        }
                        .toSet()
                }
            }
    }

    suspend fun removeKey(userEmailKey: Preferences.Key<String>) {
        dataStore.edit {
            it.remove(key = userEmailKey)
        }
    }

    suspend fun clear() {
        dataStore
            .edit {
                it.clear()
            }
    }
}

fun <T : Any> String.deserializeFromJson(objClass: Class<T>): T? =
    Moshi.Builder().build().adapter(objClass).fromJson(this)

fun <T : Any> serializeToJson(obj: T): String =
    Moshi.Builder().build().adapter(obj.javaClass).toJson(obj)
