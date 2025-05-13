package com.example.settings.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.settings.repositories.SettingsRepository
import com.example.settings.repositories.SettingsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


val Context.settingsDataStore : DataStore<Preferences> by preferencesDataStore("settingsDataStore")

@Module
@InstallIn(SingletonComponent::class)
class SettingsHiltModule{

    @Provides
    @Singleton
    fun resolveDataStore(@ApplicationContext context: Context) : DataStore<Preferences> {
        return context.settingsDataStore
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class SettingsHiltModule1{
    @Binds
    abstract fun bindSettingsRepository(impl: SettingsRepositoryImpl) : SettingsRepository
}
