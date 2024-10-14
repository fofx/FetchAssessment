package com.fofxlabs.fetchassessment.dagger

import android.content.Context
import androidx.room.Room
import com.fofxlabs.fetchassessment.data.local.FetchAssessmentDatabase
import com.fofxlabs.fetchassessment.data.local.ItemDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): FetchAssessmentDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            FetchAssessmentDatabase::class.java,
            "FetchAssessmentDatabase.db"
        ).build()
    }

    @Provides
    fun provideItemDao(database: FetchAssessmentDatabase): ItemDao = database.itemDao()

}