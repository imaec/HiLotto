package com.imaec.data.di

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.imaec.data.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkCoreModule {

    const val HOST_SAMPLE = "sample"

    @Provides
    @Singleton
    fun provideConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideInterceptor(@ApplicationContext context: Context): Interceptor =
        Interceptor { chain ->
            val requestBuilder =
                chain
                    .request()
                    .newBuilder()
            val response = chain.proceed(requestBuilder.build())
            if (!response.isSuccessful) {
                handleError(response, context)
            }
            response
        }

    /**
     * 200이외의 에러일때 공통으로 처리하는 부분.
     * 200, 401이외의 code는 우선 공통으로 toast호출
     */
    private fun handleError(response: Response, context: Context) {
        showNetworkErrorToast(response, context)
    }

    private fun showNetworkErrorToast(response: Response, context: Context) {
        Handler(Looper.getMainLooper()).post {
            val message = "네트워크 오류[${response.code}, ${response.message}]"
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

    @Provides
    @Singleton
    @Named(HOST_SAMPLE)
    fun provideOkHttpClient(
        interceptor: Interceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addNetworkInterceptor(httpLoggingInterceptor)
            .readTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)

        return okHttpClient.build()
    }

    @Provides
    @Singleton
    @Named(HOST_SAMPLE)
    fun provideServerRetrofitBuilder(
        gsonConverterFactory: GsonConverterFactory,
        @Named(HOST_SAMPLE) okHttpClient: OkHttpClient,
        @Named(HOST_SAMPLE) host: String
    ): Retrofit.Builder = Retrofit
        .Builder()
        .baseUrl(host)
        .client(okHttpClient)
        .addConverterFactory(gsonConverterFactory)
}
