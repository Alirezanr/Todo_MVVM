package dan.nr.myapplication.repository

import dan.nr.myapplication.base.BaseRepository
import dan.nr.myapplication.network.AuthApi
import dan.nr.myapplication.util.safeApiCall
import javax.inject.Inject

class AuthRepository @Inject constructor(var api: AuthApi) : BaseRepository()
{
    suspend fun login(email: String, password: String) = safeApiCall {
        api.login(email, password)
    }

    suspend fun signUp(name: String,
                       email: String,
                       password: String,
                       confirmedPassword: String) = safeApiCall {
        api.signup(name, email, password, confirmedPassword)
    }

    suspend fun emailCheck(email: String) = safeApiCall {
        api.emailCheck(email)
    }
}