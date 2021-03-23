package com.abrar.akudimana.core.di.module

import android.app.Application
import androidx.room.Room
import com.abrar.akudimana.database.LocationDao
import com.abrar.akudimana.database.LocationDatabase
import com.abrar.akudimana.domain.repository.LocationRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val DatabaseModule = module {
    fun provideDatabase(application: Application): LocationDatabase {
        return Room.databaseBuilder(application, LocationDatabase::class.java, "location")
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideLocation(database: LocationDatabase): LocationDao {
        return database.locationDao()
    }
    fun provideLocationRepository(locationDao: LocationDao): LocationRepository {
        return LocationRepository(locationDao)
    }

    single { provideLocationRepository(get()) }
    single { provideDatabase(androidApplication()) }
    single { provideLocation(get()) }
}
