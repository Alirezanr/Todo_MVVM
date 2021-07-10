package dan.nr.myapplication.di

import androidx.viewbinding.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dan.nr.myapplication.network.AuthApi
import dan.nr.myapplication.network.RemoteDataSource
import dan.nr.myapplication.network.TodoApi
import dan.nr.myapplication.util.BASE_URL
import dan.nr.myapplication.util.UserPreferences
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule
{
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit.Builder
    {
        return Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpClient.Builder()
                                .addInterceptor { chain ->
                                    chain.proceed(chain.request().newBuilder().also {
                                        it.addHeader("Content-Type", "application/json")
                                    }.build())
                                }.also { client ->
                            if (BuildConfig.DEBUG)
                            {
                                val logging = HttpLoggingInterceptor()
                                logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                                client.addInterceptor(logging)
                            }
                        }.build())

    }

    @Singleton
    @Provides
    fun provideAuthService(retrofit: Retrofit.Builder): AuthApi
    {
        return retrofit
                .build()
                .create(AuthApi::class.java)
    }

    @Singleton
    @Provides
    fun provideTodoApi(userPreferences: UserPreferences,
                       remoteDataSource: RemoteDataSource): TodoApi
    {
        val authToken: String? = runBlocking { userPreferences.authToken.first() }
        return remoteDataSource.buildApi(TodoApi::class.java, authToken)
    }
}