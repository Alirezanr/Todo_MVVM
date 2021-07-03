package dan.nr.myapplication.ui.authentication.sign_up

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
import dan.nr.myapplication.databinding.FragmentSignUpBinding
import dan.nr.myapplication.model.auth.AuthResponse
import dan.nr.myapplication.util.*
import dan.nr.myapplication.viewmodel.AuthViewModel
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SignupFragment : BaseFragment<FragmentSignUpBinding>()
{
    private val viewModel: AuthViewModel by viewModels()

    private val name get() = binding.edtNameFragmentSignup.editText?.text.toString()
    private val email get() = binding.edtEmailFragmentSigniup.editText?.text.toString()
    private val password get() = binding.edtPasswordFragmentSignup.editText?.text.toString()
    private val confirmedPassword get() = binding.edtConfirmPasswordFragmentSignup.editText?.text.toString()
    private val txtLogin get() = binding.txtLogInFrSignup
    private lateinit var btnSignup: CircularProgressButton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        btnSignup = requireView().findViewById(R.id.btn_signup_fragment_signup)
        subscribeObservers()
        btnSignup.setOnClickListener {
            if (inputsAreValid())
            {
                signUp()
            }
        }

        txtLogin?.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
        }
    }

    private fun subscribeObservers()
    {
        lifecycleScope.launchWhenStarted {
            viewModel.signUpEventFlow.collect { response ->
                Log.i(TAG, "subscribeObservers: $response")
                when (response)
                {
                    is Resource.Loading ->
                    {
                        btnSignup.startAnimation()
                        setClickable(false)
                    }
                    is Resource.Success<AuthResponse> ->
                    {
                        Log.i(TAG, "subscribeObservers:Resource.Success-> response=${response.data}")
                        btnSignup.stopAnimation()
                        setClickable(false)
                        requireView().snackBar("Successfully signed up")
                        val authToken = response.data.user?.accessToken
                        if (authToken != null)
                        {
                            userPreferences.clear()
                            userPreferences.saveAuthToken(authToken)
                        }
                    }
                    is Resource.Error ->
                    {
                        btnSignup.revertAnimation()
                        setClickable(true)
                        handleApiError(response)
                    }
                }
            }
        }

    }

    private fun signUp()
    {
        viewModel.signUp(name,email,password,confirmedPassword)
    }

    private fun setClickable(canUserClick: Boolean)
    {
        btnSignup.isClickable = canUserClick
        txtLogin?.isClickable = canUserClick
    }

    private fun inputsAreValid(): Boolean
    {
        binding.edtNameFragmentSignup.isErrorEnabled = false
        binding.edtEmailFragmentSigniup.isErrorEnabled = false
        binding.edtPasswordFragmentSignup.isErrorEnabled = false
        binding.edtConfirmPasswordFragmentSignup.isErrorEnabled = false
        if (name.isEmpty())
        {
            binding.edtNameFragmentSignup.error = "Name field can't be empty"
            return false
        }
        if (email.isEmpty())
        {
            binding.edtEmailFragmentSigniup.error = "Email  field can't be empty"
            return false
        }
        if (!email.isValidEmail())
        {
            binding.edtEmailFragmentSigniup.error = "Invalid Email Address"
            return false
        }
        if (password.length < 8)
        {
            binding.edtPasswordFragmentSignup.error = "Password should have at least 8 characters."
            return false
        }
        if (password != confirmedPassword)
        {
            binding.edtConfirmPasswordFragmentSignup.error = "Passwords not match"
            return false
        }
        return true
    }

    override fun getFragmentBinding(inflater: LayoutInflater,
                                    container: ViewGroup?): FragmentSignUpBinding =
            FragmentSignUpBinding.inflate(inflater,
                                          container,
                                          false)

    override fun onDestroy()
    {
        super.onDestroy()
        viewModel.clearAll()
    }
}