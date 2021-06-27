package dan.nr.myapplication.ui.authentication.sign_up

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dan.nr.myapplication.base.BaseFragment
import dan.nr.myapplication.databinding.FragmentSignInBinding
import dan.nr.myapplication.viewmodel.AuthViewModel

@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding>()
{
    private val viewModel:AuthViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSigninFragmentSignin.setOnClickListener {
            Toast.makeText(requireContext(), "toast", Toast.LENGTH_SHORT).show()
        }
    }
    override fun getFragmentBinding(inflater: LayoutInflater,
                                    container: ViewGroup?): FragmentSignInBinding =
            FragmentSignInBinding.inflate(inflater,
                                          container,
                                          false)

}