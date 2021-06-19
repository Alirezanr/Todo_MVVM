package dan.nr.myapplication.base

import androidx.lifecycle.ViewModel
import dan.nr.myapplication.network.AuthenticationApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BaseViewModel(private val repository: BaseRepository) : ViewModel()
{
    suspend fun logout(api: AuthenticationApi) = withContext(Dispatchers.IO) {
        repository.logout(api)
    }
}