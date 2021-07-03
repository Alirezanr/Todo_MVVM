package dan.nr.myapplication.ui.splash

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dan.nr.myapplication.R
import dan.nr.myapplication.util.TAG
import dan.nr.myapplication.util.UserPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.fragment_splash)
{
    @Inject
    lateinit var userPreferences: UserPreferences

    @InternalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenStarted {
            withContext(Dispatchers.Main) {
                userPreferences.authToken.collect {
                    delay(400)
                    if (it == null)
                    {
                        //todo : navigate to home screen
                        Log.i(TAG, "onViewCreated: its null")
                    } else
                    {
                        findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
                    }
                }
            }
        }
    }
}