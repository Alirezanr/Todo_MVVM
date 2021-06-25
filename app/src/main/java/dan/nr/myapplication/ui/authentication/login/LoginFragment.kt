package dan.nr.myapplication.ui.authentication.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import br.com.simplepass.loadingbutton.customViews.CircularProgressButton
import dagger.hilt.android.AndroidEntryPoint
import dan.nr.myapplication.R
import dan.nr.myapplication.base.BaseFragment
import dan.nr.myapplication.databinding.FragmentLoginBinding
import dan.nr.myapplication.model.auth.AuthResponse
import dan.nr.myapplication.util.*
import dan.nr.myapplication.viewmodel.AuthViewModel
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>()
{
    private val viewModel: AuthViewModel by viewModels()

    private val email get() = binding.edtEmailFragmentLogin.editText?.text.toString().trim()
    private val password get() = binding.edtPasswordFragmentLogin.editText?.text.toString().trim()

    //Because startAnimation() does not work with viewBindings we should use old findViewById way
    lateinit var btnLogin: CircularProgressButton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        btnLogin = requireActivity().findViewById(R.id.btn_login_fragment_login)
        binding.txtSignInFragmentLogin.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signInFragment)
        }
        subscribeObservers()
        btnLogin.setOnClickListener {
            if (inputsAreValid())
            {
                it.isClickable = false
                binding.txtSignInFragmentLogin.isClickable = false
                btnLogin.startAnimation()
                login()
            }
        }
    }

    private fun subscribeObservers()
    {
        viewModel.loginResponse.observe(viewLifecycleOwner, Observer { response ->
            when (response)
            {
                is Resource.Success<AuthResponse> ->
                {
                    btnLogin.revertAnimation()

                    binding.root.snackBar("Successfully logged in")
                    Log.i(TAG, "subscribeObservers:Resource.Success-> response=${response.data}")
                    lifecycleScope.launchWhenStarted {
                        val authToken: String? = response.data.user?.accessToken
                        if (authToken != null)
                        {
                            userPreferences.clear()
                            userPreferences.saveAuthToken(authToken)
                            logout()
                            //todo navigate to home
                            userPreferences.authToken.collect {
                                Log.i(TAG, "subscribeObservers: auth=$it")
                            }
                        }
                    }
                }
                is Resource.Error ->
                {
                    btnLogin.revertAnimation()
                    Toast.makeText(requireContext(), "failed", Toast.LENGTH_SHORT).show()
                    Log.i(TAG, "subscribeObservers:Resource.Error-> response=${response}")
                    this.handleApiError(response)
                }
                is Resource.Loading ->
                {

                }
            }
        })
    }

    private fun inputsAreValid(): Boolean
    {
        val email: String = binding.edtEmailFragmentLogin.editText?.text.toString().trim()
        val password: String = binding.edtPasswordFragmentLogin.editText?.text.toString().trim()
        binding.edtEmailFragmentLogin.isErrorEnabled = false
        binding.edtPasswordFragmentLogin.isErrorEnabled = false

        if (!email.isValidEmail())
        {
            binding.edtEmailFragmentLogin.error = "Invalid Email Address!"
            return false
        }
        if (password.length < 8)
        {
            binding.edtPasswordFragmentLogin.error = "Password should have at least 8 characters."
            return false
        }
        if (!email.isValidEmail())
        {
            binding.edtEmailFragmentLogin.error = "Invalid Email Address!"
            return false
        }
        if (password.length < 8)
        {
            binding.edtPasswordFragmentLogin.error = "Password should have at least 8 characters."
            return false
        }
        return true
    }

    private fun login()
    {
        viewModel.login(email, password)
    }

    override fun getFragmentBinding(inflater: LayoutInflater,
                                    container: ViewGroup?): FragmentLoginBinding =
            FragmentLoginBinding.inflate(inflater,
                                         container,
                                         false)
}