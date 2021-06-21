package dan.nr.myapplication.ui.authentication.sign_up

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import dan.nr.myapplication.R
import dan.nr.myapplication.base.BaseFragment
import dan.nr.myapplication.databinding.FragmentSignInBinding
import dan.nr.myapplication.repository.AuthRepository
import dan.nr.myapplication.ui.authentication.AuthViewModel

class SignInFragment : BaseFragment<AuthViewModel, FragmentSignInBinding, AuthRepository>()
{
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        binding.txtLogInFragmentLogin.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_loginFragment)
        }
    }

    override fun getViewModel(): Class<AuthViewModel> = AuthViewModel::class.java

    override fun getFragmentBinding(inflater: LayoutInflater,
                                    container: ViewGroup?): FragmentSignInBinding =
            FragmentSignInBinding.inflate(inflater,
                                          container,
                                          false)

    override fun getFragmentRepository(): AuthRepository = AuthRepository()
}