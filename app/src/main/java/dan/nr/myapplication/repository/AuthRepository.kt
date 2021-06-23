package dan.nr.myapplication.repository

import dan.nr.myapplication.network.AuthApi
import dan.nr.myapplication.util.safeApiCall

class AuthRepository(var api: AuthApi)
{

    /*suspend fun login(email: String, password: String): Flow<Resource<AuthResponse>> = flow {
        emit(Resource.Loading)
        try
        {
            //get data from network
            // val loginResponse=api.login(email,password)
            val loginResponse = api.login("email@email.com", "@@@@@@@@@@@@")
            //todo:send data to cache

            //todo:retrieve data from cache

            //show data in ui
            emit(Resource.Success(loginResponse))
            Log.i(TAG, "auth repo ${loginResponse}")
        } catch (e: Exception)
        {
            Log.i("!!!", "Exception in AuthRepository-> login : =\n\t\t\t$e")
            emit(Resource.Error(false,null,e.message))
        }
    }*/

    suspend fun login(email: String, password: String) = safeApiCall {
        api.login(email, password)
    }
}