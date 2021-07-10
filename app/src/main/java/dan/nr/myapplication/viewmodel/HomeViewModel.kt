package dan.nr.myapplication.viewmodel

import android.text.format.DateFormat
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dan.nr.myapplication.base.BaseViewModel
import dan.nr.myapplication.model.todo.TodoResponse
import dan.nr.myapplication.repository.HomeRepository
import dan.nr.myapplication.util.Resource
import dan.nr.myapplication.util.TAG
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(var repository: HomeRepository) : BaseViewModel(repository)
{
    private val _todos = MutableLiveData<Resource<TodoResponse>>()
    val todos: LiveData<Resource<TodoResponse>> = _todos

    fun getTodos() = viewModelScope.launch {
        _todos.postValue(Resource.Loading)
        _todos.postValue(repository.getTodos())
    }

    fun getDate(timestamp: Long): String
    {
        val calendar = Calendar.getInstance(Locale.ENGLISH)
        calendar.timeInMillis = timestamp * 1000L
        val date = DateFormat.format("dd-MM-yyyy", calendar).toString()
        return reformatDate(date)
    }

    private fun reformatDate(date: String): String
    {
        val monthList = arrayOf("January",
                                "February",
                                "March",
                                "April",
                                "May",
                                "June",
                                "July",
                                "August",
                                "September",
                                "October",
                                "November",
                                "December")

        val dateArray = date.split("-")
        val monthNum = dateArray[1].toInt()
        var formatedDate = "${monthList[monthNum]} ${dateArray[0]}, ${dateArray[2]}"

        Log.i(TAG, "dateArray: $dateArray")
        Log.i(TAG, "reformatDate: $formatedDate")
        return formatedDate
    }
}