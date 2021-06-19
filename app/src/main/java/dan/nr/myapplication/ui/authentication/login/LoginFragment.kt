package dan.nr.myapplication.ui.authentication.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dan.nr.myapplication.base.BaseFragment
import dan.nr.myapplication.databinding.FragmentLoginBinding
import dan.nr.myapplication.repository.AuthRepository
import dan.nr.myapplication.ui.authentication.AuthViewModel

class LoginFragment : BaseFragment<AuthViewModel, FragmentLoginBinding, AuthRepository>()
{
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

    }
    override fun getViewModel(): Class<AuthViewModel> = AuthViewModel::class.java

    override fun getFragmentBinding(inflater: LayoutInflater,
                                    container: ViewGroup?): FragmentLoginBinding =
            FragmentLoginBinding.inflate(inflater,
                                         container,
                                         false)

    override fun getFragmentRepository(): AuthRepository = AuthRepository()
}