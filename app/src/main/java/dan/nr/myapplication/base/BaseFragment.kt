package dan.nr.myapplication.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import dan.nr.myapplication.network.AuthApi
import dan.nr.myapplication.network.RemoteDataSource
import dan.nr.myapplication.util.UserPreferences
import kotlinx.coroutines.flow.first
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

    fun logout() = lifecycleScope.launchWhenStarted {
        val authToken = userPreferences.authToken.first()
        val api = remoteDataSource.buildApi(AuthApi::class.java, authToken)
        baseViewModel.logout(api)
        userPreferences.clear()
        //todo: navigate to login fragment here
    }

    abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): B
}
