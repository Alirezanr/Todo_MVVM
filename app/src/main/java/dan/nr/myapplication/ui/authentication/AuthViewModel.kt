package dan.nr.myapplication.ui.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dan.nr.myapplication.model.auth.AuthResponse
import dan.nr.myapplication.repository.AuthRepository
import dan.nr.myapplication.util.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(var repository: AuthRepository) : ViewModel()
{
    private val _loginResponse = MutableLiveData<Resource<AuthResponse>>()
    val loginResponse: LiveData<Resource<AuthResponse>>
        get() = _loginResponse

    fun login(email: String, password: String)
    {
        viewModelScope.launch {
            _loginResponse.postValue(repository.login(email, password))
        }
    }
}