package dan.nr.myapplication.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dan.nr.myapplication.network.AuthenticationApi
import dan.nr.myapplication.util.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule
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
    }

    @Singleton
    @Provides
    fun provideAuthService(retrofit: Retrofit.Builder): AuthenticationApi
    {
        return retrofit.build().create(AuthenticationApi::class.java)
    }
}