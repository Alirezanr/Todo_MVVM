package dan.nr.myapplication.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

suspend fun <T> safeApiCall(apiCall: suspend () -> T): Resource<T>
{
    return withContext(Dispatchers.IO) {
        try
        {
            Resource.Success(apiCall.invoke())
        } catch (t: Exception)
        {
            when (t)
            {
                is HttpException ->
                {
                    Resource.Error(false, t.code(), t.response().toString())
                }
                else ->
                {
                    Resource.Error(true, null, t.message ?: "NULL")
                }
            }
        }
    }
}

sealed class Resource<out R>
{
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val isNetworkError: Boolean,
                     val errorCode: Int?,
                     val errorBody: String?) : Resource<Nothing>()

    object Loading : Resource<Nothing>()
}