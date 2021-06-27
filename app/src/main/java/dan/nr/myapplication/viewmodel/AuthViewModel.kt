package dan.nr.myapplication.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dan.nr.myapplication.base.BaseViewModel
import dan.nr.myapplication.model.auth.AuthResponse
import dan.nr.myapplication.repository.AuthRepository
import dan.nr.myapplication.util.Resource
import dan.nr.myapplication.util.UserPreferences
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(var repository: AuthRepository) : BaseViewModel(repository)
{
    private val loginEventChennel = Channel<Resource<AuthResponse>>()
    val loginEventFlow = loginEventChennel.receiveAsFlow()

    fun login(email: String, password: String)
    {
        viewModelScope.launch {
            loginEventChennel.send(Resource.Loading)
            loginEventChennel.send(repository.login(email, password))
        }
    }

    fun saveAuthToken(authToken: String,
                      userPreferences: UserPreferences) = viewModelScope.launch {
        userPreferences.clear()
        userPreferences.saveAuthToken(authToken)
    }
}