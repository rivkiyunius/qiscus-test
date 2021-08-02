package com.rivki.samplechatsdk.module

import com.rivki.samplechatsdk.repository.DataRepository
import com.rivki.samplechatsdk.repository.DataRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun providesDataRepository(dataRepositoryImpl: DataRepositoryImpl): DataRepository = dataRepositoryImpl

}