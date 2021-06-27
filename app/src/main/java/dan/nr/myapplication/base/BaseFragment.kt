package dan.nr.myapplication.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewbinding.ViewBinding
import dan.nr.myapplication.network.RemoteDataSource
import dan.nr.myapplication.util.UserPreferences
import javax.inject.Inject

abstract class BaseFragment<B : ViewBinding> : Fragment()
{
    @Inject
    lateinit var remoteDataSource: RemoteDataSource

    @Inject
    lateinit var userPreferences: UserPreferences

    private val baseViewModel: BaseViewModel by viewModels()

    protected lateinit var binding: B

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        binding = getFragmentBinding(inflater, container)

        return binding.root
    }

    fun logout()
    {
        baseViewModel.logout(userPreferences,
                             remoteDataSource)
        //todo: navigate to login fragment here
    }

    abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): B
}
