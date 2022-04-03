package com.yasaremre.gitusersearch.network

import com.yasaremre.gitusersearch.BuildConfig
import com.yasaremre.gitusersearch.network.interceptor.ApiKeyInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun apiKeyInterceptor() = ApiKeyInterceptor()

    @Singleton
    @Provides
    fun okHttpClient(apiKeyInterceptor: ApiKeyInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                .setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE))
            .addInterceptor(apiKeyInterceptor)
        return builder.build()
    }

    @Singleton
    @Provides
    fun retrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .baseUrl(BuildConfig.API_BASE_URL)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun gitHubApiService(retrofit: Retrofit): GitHubApiService = retrofit.create(GitHubApiService::class.java)

}