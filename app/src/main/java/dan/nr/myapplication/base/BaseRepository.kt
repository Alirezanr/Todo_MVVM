package dan.nr.myapplication.base

import dan.nr.myapplication.network.AuthApi
import dan.nr.myapplication.util.safeApiCall
import javax.inject.Inject

open class BaseRepository @Inject constructor()
{
    suspend fun logout(api: AuthApi) = safeApiCall {
        api.logout()
    }
}