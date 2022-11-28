package com.jsaddour.eurosporttestapp.di
import com.jsaddour.eurosporttestapp.services.EurosportService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {

    @Singleton
    @Provides
    fun provideRetrofit() = Retrofit.Builder().apply {
        baseUrl("https://extendsclass.com/api/json-storage/")
        addConverterFactory(GsonConverterFactory.create())
    }.build()

    @Singleton
    @Provides
    fun provideEuroService(retrofit: Retrofit) = retrofit.create(EurosportService::class.java)

}