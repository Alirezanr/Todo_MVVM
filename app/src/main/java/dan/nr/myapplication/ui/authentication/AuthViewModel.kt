package dan.nr.myapplication.ui.authentication

import dan.nr.myapplication.base.BaseViewModel
import dan.nr.myapplication.repository.AuthRepository

class AuthViewModel(val repository: AuthRepository) : BaseViewModel(repository)
{
}