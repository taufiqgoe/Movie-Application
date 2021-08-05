package com.example.movieapplication.di

import android.app.Application
import androidx.room.Room
import com.example.movieapplication.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application) = Room.databaseBuilder(
        application,
        AppDatabase::class.java, "app_database"
    ).build()

    @Provides
    @Singleton
    fun provideMovieDao(database: AppDatabase) = database.movieDao()

    @Provides
    @Singleton
    fun provideRemoteKeyDao(database: AppDatabase) = database.popularKeyDao()
}