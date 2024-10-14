package com.fofxlabs.fetchassessment.dagger

import com.fofxlabs.fetchassessment.data.DataRepository
import com.fofxlabs.fetchassessment.data.DefaultDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun provideDataRepository(dataRepository: DefaultDataRepository): DataRepository
}