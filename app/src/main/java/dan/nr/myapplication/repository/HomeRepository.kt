package dan.nr.myapplication.repository

import dan.nr.myapplication.base.BaseRepository
import dan.nr.myapplication.network.TodoApi
import dan.nr.myapplication.util.safeApiCall

class HomeRepository(private val api: TodoApi) : BaseRepository()
{
    suspend fun getTodos() = safeApiCall {
        api.getTodos()
    }
}