package dan.nr.myapplication.viewmodel

import android.util.Log
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
            Log.i(TAG, "viewModel: signUp")
            signUpEventChannel.send(Resource.Loading)
            signUpEventChannel.send(repository.signUp(name, email, password, confirmedPassword))
        }
    }

    fun saveAuthToken(authToken: String,
                      userPreferences: UserPreferences) = viewModelScope.launch {
        userPreferences.clear()
        userPreferences.saveAuthToken(authToken)
    }
    fun clearAll()
    {
        onCleared()
    }
    override fun onCleared()
    {
        super.onCleared()
        Log.i(TAG, "onCleared: ")
    }
}