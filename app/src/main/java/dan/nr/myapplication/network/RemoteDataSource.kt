package dan.nr.myapplication.network

import androidx.viewbinding.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Inject


class RemoteDataSource @Inject constructor(var retrofit: Retrofit.Builder)
{
    fun <Api> buildApi(api: Class<Api>, authToken: String? = null): Api
    {
        return retrofit
                .client(OkHttpClient.Builder()
                                .addInterceptor { chain ->
                                    chain.proceed(chain.request().newBuilder().also {
                                        it.addHeader("Authorization", "Bearer $authToken")
                                    }.build())

                                }.also { client ->
                            if (BuildConfig.DEBUG)
                            {
                                val logging = HttpLoggingInterceptor()
                                logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                                client.addInterceptor(logging)
                            }
                        }.build())
                .build()
                .create(api)
    }
}