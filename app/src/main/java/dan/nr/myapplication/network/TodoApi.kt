package dan.nr.myapplication.network

import dan.nr.myapplication.model.todo.TodoResponse
import retrofit2.http.POST

interface TodoApi
{
    @POST("todos")
    suspend fun getTodos(): TodoResponse
}