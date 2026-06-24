package com.app.criba.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.app.criba.data.local.dao.UserDao
import com.app.criba.data.local.entity.UserEntity
import com.app.criba.domain.model.User
import com.app.criba.domain.repository.AuthRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

private val Context.sessionDataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

@Singleton
class AuthRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val userDao: UserDao
) : AuthRepository {

    companion object {
        private val KEY_USER_ID = stringPreferencesKey("user_id")
        private val KEY_EMAIL = stringPreferencesKey("email")
        private val KEY_DISPLAY_NAME = stringPreferencesKey("display_name")
        private val KEY_PHOTO_URL = stringPreferencesKey("photo_url")
    }

    override suspend fun login(email: String, passwordHash: String): User {
        val userEntity = userDao.getUserByEmail(email)
        if (userEntity != null && userEntity.passwordHash == passwordHash) {
            val user = User(
                id = userEntity.id,
                email = userEntity.email,
                displayName = userEntity.displayName,
                photoUrl = userEntity.photoUrl
            )
            return signInAndSave(user)
        } else {
            throw Exception("Credenciales incorrectas")
        }
    }

    override suspend fun loginWithGoogle(email: String, displayName: String, photoUrl: String?): User {
        val existingUser = userDao.getUserByEmail(email)
        val user = if (existingUser != null) {
            User(
                id = existingUser.id,
                email = existingUser.email,
                displayName = existingUser.displayName,
                photoUrl = photoUrl ?: existingUser.photoUrl
            )
        } else {
            val newUser = UserEntity(
                id = UUID.randomUUID().toString(),
                email = email,
                passwordHash = null,
                displayName = displayName,
                photoUrl = photoUrl
            )
            userDao.insertUser(newUser)
            User(
                id = newUser.id,
                email = newUser.email,
                displayName = newUser.displayName,
                photoUrl = newUser.photoUrl
            )
        }
        return signInAndSave(user)
    }

    override suspend fun register(email: String, passwordHash: String, displayName: String): User {
        val existing = userDao.getUserByEmail(email)
        if (existing != null) {
            throw Exception("El correo ya está registrado")
        }
        val newUser = UserEntity(
            id = UUID.randomUUID().toString(),
            email = email,
            passwordHash = passwordHash,
            displayName = displayName,
            photoUrl = null
        )
        userDao.insertUser(newUser)
        val user = User(id = newUser.id, email = newUser.email, displayName = newUser.displayName, photoUrl = null)
        return signInAndSave(user)
    }

    private suspend fun signInAndSave(user: User): User {
        context.sessionDataStore.edit { prefs ->
            prefs[KEY_USER_ID] = user.id
            prefs[KEY_EMAIL] = user.email
            prefs[KEY_DISPLAY_NAME] = user.displayName
            prefs[KEY_PHOTO_URL] = user.photoUrl ?: ""
        }
        return user
    }

    override suspend fun getCurrentUser(): User? {
        return context.sessionDataStore.data.map { prefs ->
            val id = prefs[KEY_USER_ID] ?: return@map null
            val email = prefs[KEY_EMAIL] ?: return@map null
            val name = prefs[KEY_DISPLAY_NAME] ?: ""
            val photo = prefs[KEY_PHOTO_URL]?.ifBlank { null }
            User(id = id, email = email, displayName = name, photoUrl = photo)
        }.firstOrNull()
    }

    override suspend fun signOut() {
        context.sessionDataStore.edit { it.clear() }
    }

    override suspend fun isLoggedIn(): Boolean {
        return getCurrentUser() != null
    }
}
