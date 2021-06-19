package dan.nr.myapplication.base

import dan.nr.myapplication.network.AuthenticationApi
import dan.nr.myapplication.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class BaseRepository
{
    suspend fun <T> safeApiCall(apiCall: suspend () -> T): Resource<T>
    {
        return withContext(Dispatchers.IO) {
            try
            {
                Resource.success(apiCall.invoke())
            } catch (t: Throwable)
            {
                when (t)
                {
                    is HttpException ->
                    {
                        Resource.error(false, t.response()?.errorBody().toString(), null)
                    }
                    else             ->
                    {
                        Resource.error(true, null, null)
                    }
                }
            }
        }
    }

    suspend fun logout(api: AuthenticationApi) = safeApiCall {
        api.logout()
    }
}