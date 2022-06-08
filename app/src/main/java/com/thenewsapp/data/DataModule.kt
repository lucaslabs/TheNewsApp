package com.thenewsapp.data

import android.content.Context
import androidx.room.Room
import com.thenewsapp.data.db.QueryDao
import com.thenewsapp.data.db.QueryDatabase
import com.thenewsapp.data.net.ApiKeyInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideApiKeyInterceptor() = ApiKeyInterceptor()

    @Provides
    @Singleton
    fun provideHttpClient(apiKeyInterceptor: ApiKeyInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideNewsService(
        httpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
    ): NewsService =
        Retrofit.Builder()
            .baseUrl(NewsService.BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .client(httpClient)
            .build()
            .create(NewsService::class.java)

    @Provides
    @Singleton
    fun provideQueryDatabase(@ApplicationContext context: Context): QueryDatabase =
        Room.databaseBuilder(
            context,
            QueryDatabase::class.java,
            QueryDatabase.DATABASE_NAME
        ).build()

    @Provides
    @Singleton
    fun provideQueryDao(queryDatabase: QueryDatabase): QueryDao =
        queryDatabase.queryDao()

}