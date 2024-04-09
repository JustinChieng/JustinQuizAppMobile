package com.justin.justinquizapp21.core.di

import com.justin.justinquizapp21.core.service.AuthService
import com.justin.justinquizapp21.data.repo.QuizsRepo
import com.justin.justinquizapp21.data.repo.QuizsRepoImpl
import com.justin.justinquizapp21.data.repo.ScoreRepo
import com.justin.justinquizapp21.data.repo.ScoreRepoImpl
import com.justin.justinquizapp21.data.repo.UserRepo
import com.justin.justinquizapp21.data.repo.UserRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepoModule {
    @Provides
    @Singleton
    fun providesUserRepo(authService: AuthService): UserRepo {
        return UserRepoImpl(authService = authService)
    }

    @Provides
    @Singleton
    fun provideQuizsRepo(authService: AuthService): QuizsRepo {
        return QuizsRepoImpl(authService = authService)
    }

    @Provides
    @Singleton
    fun provideScoreRepo(authService: AuthService): ScoreRepo {
        return ScoreRepoImpl(authService = authService)
    }
}