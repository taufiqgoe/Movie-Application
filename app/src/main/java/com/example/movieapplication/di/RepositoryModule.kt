package com.example.movieapplication.di

import com.example.movieapplication.api.Api
import com.example.movieapplication.database.AppDatabase
import com.example.movieapplication.repositories.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(api: Api, database: AppDatabase): Repository = Repository(api, database.movieDao(), database)
}