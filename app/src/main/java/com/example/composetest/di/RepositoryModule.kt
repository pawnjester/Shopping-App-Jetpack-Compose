package com.example.composetest.di

import com.example.composetest.repository.Repository
import com.example.composetest.repository.RepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun providesRepository(repositoryImpl: RepositoryImpl) : Repository
}