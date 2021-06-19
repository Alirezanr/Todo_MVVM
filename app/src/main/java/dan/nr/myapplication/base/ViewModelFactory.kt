package dan.nr.myapplication.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dan.nr.myapplication.repository.AuthRepository
import dan.nr.myapplication.ui.authentication.AuthViewModel

class ViewModelFactory(private val repository: BaseRepository) : ViewModelProvider.NewInstanceFactory()
{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T
    {
        return when
        {
            modelClass.isAssignableFrom(AuthViewModel::class.java) ->
            {
                AuthViewModel(repository = repository as AuthRepository) as T
            }
            else                                                   ->
            {
                throw IllegalArgumentException("ViewModel class not found")
            }
        }
    }
}