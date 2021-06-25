package dan.nr.myapplication.base

import dan.nr.myapplication.network.AuthApi
import dan.nr.myapplication.util.safeApiCall

open  class BaseRepository
{
    suspend fun logout(api: AuthApi) = safeApiCall {
        api.logout()
    }
}