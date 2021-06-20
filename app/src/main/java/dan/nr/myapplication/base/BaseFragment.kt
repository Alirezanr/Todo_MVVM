package dan.nr.myapplication.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import dagger.hilt.android.AndroidEntryPoint
import dan.nr.myapplication.network.AuthenticationApi
import dan.nr.myapplication.network.RemoteDataSource
import dan.nr.myapplication.util.UserPreferences
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class BaseFragment<VM : BaseViewModel, B : ViewBinding, R : BaseRepository> : Fragment()
{
    @Inject
    lateinit var remoteDataSource: RemoteDataSource

    @Inject
    lateinit var userPreferences: UserPreferences

    protected lateinit var binding: B
    protected lateinit var viewModel: VM

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        binding = getFragmentBinding(inflater, container)
        val factory = ViewModelFactory(getFragmentRepository())
        viewModel = ViewModelProvider(this, factory).get(getViewModel())


        return binding.root
    }

    fun logout() = lifecycleScope.launchWhenStarted {
        val authToken = userPreferences.authToken.first()
        val api = remoteDataSource.buildApi(AuthenticationApi::class.java, authToken)
        viewModel.logout(api = api)
        userPreferences.clear()
        //todo: navigate to login fragment here
    }

    abstract fun getViewModel(): Class<VM>
    abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): B
    abstract fun getFragmentRepository(): R
}