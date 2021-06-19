package dan.nr.myapplication.network

import dan.nr.myapplication.model.auth.AuthModel
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.POST

interface AuthenticationApi
{
    @POST("auth/signup")
    suspend fun signup(@Field("name") name: String,
                       @Field("email") email: String,
                       @Field("password") password: String,
                       @Field("password_confirmation") password_confirmation: String): Response<AuthModel>


    @POST("auth/login")
    suspend fun login(@Field("email") email: String,
                      @Field("password") password: String): Response<AuthModel>

    @POST("logout")
    suspend fun logout(): Response<AuthModel>
}