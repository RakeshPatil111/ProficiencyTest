package com.example.proficiencytest.di

import android.content.Context
import androidx.room.Room
import com.example.proficiencytest.repository.FactRepositoryImpl
import com.example.proficiencytest.repository.FactRepository
import com.example.proficiencytest.repository.db.FactDAO
import com.example.proficiencytest.repository.db.FactDatabase
import com.example.proficiencytest.repository.network.FactAPI
import com.example.proficiencytest.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {
    @Provides
    fun provideFactDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        FactDatabase::class.java,
        Constants.DB_NAME
    ).build()

    @Singleton
    @Provides
    fun provideFactDAO(database: FactDatabase) = database.getFactDao()

    @Provides
    fun provideDefaultRepository(dao: FactDAO, api: FactAPI) = FactRepositoryImpl(dao, api) as FactRepository

    @Singleton
    @Provides
    fun provideFactAPI(): FactAPI {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FactAPI::class.java)
    }
}