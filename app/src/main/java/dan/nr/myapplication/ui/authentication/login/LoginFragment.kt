package dan.nr.myapplication.ui.authentication.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
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
    private val txtSignup get() = binding.txtSignInFragmentLogin

    //Because startAnimation() does not work with viewBindings we should use old findViewById way
    lateinit var btnLogin: CircularProgressButton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        btnLogin = requireView().findViewById(R.id.btn_login_fragment_login)

        binding.txtSignInFragmentLogin.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        subscribeObservers()
        btnLogin.setOnClickListener {
            if (inputsAreValid())
            {
                login()
            }
        }
    }

    private fun subscribeObservers()
    {
        lifecycleScope.launchWhenStarted {
            viewModel.loginEventFlow.collect { response ->
                when (response)
                {
                    is Resource.Success<AuthResponse> ->
                    {
                        Log.i(TAG, "subscribeObservers:Resource.Success-> response=${response.data}")
                        setClickable(false)
                        btnLogin.stopAnimation()
                        requireView().snackBar("Successfully logged in", null, GREEN_SUCCESS, WHITE)
                        val authToken: String? = response.data.user?.accessToken
                        if (authToken != null)
                        {
                            viewModel.saveAuthToken(authToken,
                                                    userPreferences)
                            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                        }
                    }
                    is Resource.Error ->
                    {
                        Log.i(TAG, "subscribeObservers:Resource.Error-> response=${response}")
                        btnLogin.revertAnimation()
                        setClickable(true)
                        this@LoginFragment.handleApiError(response) {
                            login()
                        }
                    }
                    is Resource.Loading ->
                    {
                        setClickable(false)
                        btnLogin.startAnimation()
                    }
                }
            }
        }
    }

    private fun setClickable(canUserClick: Boolean)
    {
        btnLogin.isClickable = canUserClick
        txtSignup.isClickable = canUserClick
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