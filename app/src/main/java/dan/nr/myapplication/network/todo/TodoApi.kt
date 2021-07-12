package dan.nr.myapplication.network.todo

import retrofit2.http.POST

interface TodoApi
{
    @POST("todos")
    suspend fun getTodos(): TodoResponse
}