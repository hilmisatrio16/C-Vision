package com.capstoneproject.cvision.data.dsprefs

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.concurrent.Volatile

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "authentication")

class AuthPreferences private constructor(private val dataStore: DataStore<Preferences>) {
    private val IS_LOGIN = booleanPreferencesKey("is_login")
    private val TOKEN = stringPreferencesKey("token")
    private val NAME_USER = stringPreferencesKey("name_user")
    private val USERNAME = stringPreferencesKey("username")

    suspend fun saveDataPrefs(userIsLogin: Boolean, token: String, nameUser: String, username: String){
        dataStore.edit {
            it[IS_LOGIN] = userIsLogin
            it[TOKEN] = token
            it[NAME_USER] =  nameUser
            it[USERNAME] = username
        }
    }

    suspend fun clearDataPrefs(){
        dataStore.edit {
            it.clear()
        }
    }

    fun getSession(): Flow<Boolean>{
        return dataStore.data.map {
            it[IS_LOGIN] ?: false
        }
    }

    fun getToken(): Flow<String>{
        return dataStore.data.map {
            it[TOKEN] ?: ""
        }
    }

    fun getNameUser(): Flow<String>{
        return dataStore.data.map {
            it[NAME_USER] ?: ""
        }
    }

    fun getUsername(): Flow<String>{
        return dataStore.data.map {
            it[USERNAME] ?: ""
        }
    }

    companion object{
        @Volatile
        private var INSTANCE: AuthPreferences? = null
        fun getInstance(dataStore: DataStore<Preferences>): AuthPreferences{
            return INSTANCE ?: synchronized(this){
                val instance = AuthPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}