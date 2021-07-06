package dan.nr.myapplication.ui.authentication.sign_up

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
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
import kotlinx.coroutines.*
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

    private var job: Job? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        subscribeObservers()
        btnSignup = requireView().findViewById(R.id.btn_signup_fragment_signup)
        binding.edtEmailFragmentSigniup.editText?.addTextChangedListener { editable ->
            job?.cancel()
            if (email.isValidEmail())
            {
                job = CoroutineScope(Dispatchers.Main).launch {
                    delay(600)
                    editable.let {
                        if (email.isNotEmpty())
                        {
                            Log.i(TAG, "onViewCreated: $email")
                            viewModel.emailCheck(email)
                        }
                    }
                }
            }
        }


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
                        requireView().snackBar("Successfully signed up", null, GREEN_SUCCESS, WHITE)
                        val authToken = response.data.user?.accessToken
                        if (authToken != null)
                        {
                            userPreferences.clear()
                            userPreferences.saveAuthToken(authToken)
                            findNavController().navigate(R.id.action_signUpFragment_to_homeFragment)
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
        viewModel.emailCheckResponse.observe(viewLifecycleOwner) { respons ->
            when (respons)
            {
                is Resource.Loading ->
                {
                    binding.edtEmailFragmentSigniup.isErrorEnabled = false

                    binding.edtEmailFragmentSigniup.isHelperTextEnabled = false
                    binding.edtEmailFragmentSigniup.isErrorEnabled = false
                }
                is Resource.Success ->
                {
                    binding.edtEmailFragmentSigniup.isErrorEnabled = true
                    binding.edtEmailFragmentSigniup.isHelperTextEnabled = true
                    binding.edtEmailFragmentSigniup.helperText = "You can choose this email."

                    Log.i(TAG, "subscribeObservers: Success:$respons")
                }
                is Resource.Error ->
                {
                    binding.edtEmailFragmentSigniup.isErrorEnabled = true
                    if(respons.errorCode==409)
                    {
                        binding.edtEmailFragmentSigniup.error="This email is already taken"
                    }
                    Log.i(TAG, "subscribeObservers: Error:$respons")
                }
            }
        }
    }

    private fun signUp()
    {
        viewModel.signUp(name, email, password, confirmedPassword)
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
}