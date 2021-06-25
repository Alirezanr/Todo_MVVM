package dan.nr.myapplication.base

import androidx.lifecycle.ViewModel
import dan.nr.myapplication.network.AuthApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open  class BaseViewModel(private val repository: BaseRepository) : ViewModel()
{
    suspend fun logout(api: AuthApi) = withContext(Dispatchers.IO) {
        repository.logout(api)
    }
}