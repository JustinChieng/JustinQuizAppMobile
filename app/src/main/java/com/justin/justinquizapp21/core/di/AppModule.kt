package com.justin.justinquizapp21.core.di

import com.justin.justinquizapp21.core.service.AuthService
import com.justin.justinquizapp21.core.service.AuthServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton //tell compiler only need 1 object
    fun provideAuthService(): AuthService {
        return AuthServiceImpl()
    }
}