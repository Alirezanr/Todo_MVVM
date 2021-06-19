package dan.nr.myapplication.di

import androidx.viewbinding.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dan.nr.myapplication.network.AuthenticationApi
import dan.nr.myapplication.util.BASE_URL
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
    fun provideGsonBuilder(): Gson = GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create()

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit.Builder
    {
        return Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
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
    fun provideAuthService(retrofit: Retrofit.Builder): AuthenticationApi
    {
        return retrofit
                .build()
                .create(AuthenticationApi::class.java)
    }
}