package dan.nr.myapplication.network

import dan.nr.myapplication.model.auth.AuthResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApi
{
    @FormUrlEncoded
    @POST("auth/signup")
    suspend fun signup(@Field("name") name: String,
                       @Field("email") email: String,
                       @Field("password") password: String,
                       @Field("password_confirmation") password_confirmation: String): AuthResponse

    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(@Field("email") email: String,
                      @Field("password") password: String): AuthResponse

    @POST("logout")
    suspend fun logout(): AuthResponse
}