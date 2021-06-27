package dan.nr.myapplication.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dan.nr.myapplication.network.AuthApi
import dan.nr.myapplication.network.RemoteDataSource
import dan.nr.myapplication.util.TAG
import dan.nr.myapplication.util.UserPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

open class BaseViewModel @Inject constructor(private val repository: BaseRepository) : ViewModel()
{
    fun logout(userPreferences: UserPreferences,
               remoteDataSource: RemoteDataSource) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            val authToken = userPreferences.authToken.first()
            val api = remoteDataSource.buildApi(AuthApi::class.java, authToken)

            repository.logout(api)
        }
    }
}