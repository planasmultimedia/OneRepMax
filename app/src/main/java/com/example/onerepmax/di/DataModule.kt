package com.example.onerepmax.di

import android.content.Context
import com.example.onerepmax.data.local.AssetFileReader
import com.example.onerepmax.data.local.FileReader
import com.example.onerepmax.data.repository.ExerciseLocalTxtRepositoryImpl
import com.example.onerepmax.data.repository.ExerciseRepository
import com.example.onerepmax.domain.usecase.BrzyckiMaxOneRepCalculator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Singleton
    @Provides
    fun provideFileReader(@ApplicationContext context: Context): FileReader {
        return AssetFileReader(context)
    }

    @Singleton
    @Provides
    fun provideWorkoutRepository(fileReader: FileReader): ExerciseRepository {
        return ExerciseLocalTxtRepositoryImpl(fileReader)
    }

    @Singleton
    @Provides
    fun provideCalculator(): BrzyckiMaxOneRepCalculator {
        return BrzyckiMaxOneRepCalculator()
    }
}