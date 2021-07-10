package dan.nr.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dan.nr.myapplication.base.BaseViewModel
import dan.nr.myapplication.model.auth.AuthResponse
import dan.nr.myapplication.repository.AuthRepository
import dan.nr.myapplication.util.Resource
import dan.nr.myapplication.util.TAG
import dan.nr.myapplication.util.UserPreferences
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(var repository: AuthRepository) : BaseViewModel(repository)
{
    private val loginEventChannel = Channel<Resource<AuthResponse>>()
    val loginEventFlow = loginEventChannel.receiveAsFlow()

    private val signUpEventChannel = Channel<Resource<AuthResponse>>()
    val signUpEventFlow = signUpEventChannel.receiveAsFlow()

    private val _emailCheckResponse = MutableLiveData<Resource<AuthResponse>>()
    val emailCheckResponse: LiveData<Resource<AuthResponse>> = _emailCheckResponse

    fun login(email: String, password: String)
    {
        viewModelScope.launch {
            loginEventChannel.send(Resource.Loading)
            loginEventChannel.send(repository.login(email, password))
        }
    }

    fun signUp(name: String, email: String, password: String, confirmedPassword: String)
    {
        viewModelScope.launch {
            signUpEventChannel.send(Resource.Loading)
            signUpEventChannel.send(repository.signUp(name, email, password, confirmedPassword))
        }
    }

    fun emailCheck(email: String)
    {
        viewModelScope.launch {
            _emailCheckResponse.postValue(Resource.Loading)
            _emailCheckResponse.postValue(repository.emailCheck(email))
        }
    }

    fun saveAuthToken(authToken: String,
                      userPreferences: UserPreferences) = viewModelScope.launch {
        userPreferences.clear()
        userPreferences.saveAuthToken(authToken)
    }
}