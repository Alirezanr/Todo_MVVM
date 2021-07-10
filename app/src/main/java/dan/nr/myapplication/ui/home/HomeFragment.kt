package dan.nr.myapplication.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dan.nr.myapplication.base.BaseFragment
import dan.nr.myapplication.databinding.FragmentHomeBinding
import dan.nr.myapplication.model.todo.TodoResponse
import dan.nr.myapplication.util.Resource
import dan.nr.myapplication.util.TAG
import dan.nr.myapplication.util.handleApiError
import dan.nr.myapplication.viewmodel.HomeViewModel

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>()
{
    val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        subscribeObservers()
        viewModel.getTodos()

        binding.txtDateFrHome.text = viewModel.getDate(System.currentTimeMillis() / 1000)
    }

    private fun subscribeObservers()
    {
        viewModel.todos.observe(viewLifecycleOwner) { response ->
            Log.i(TAG, "subscribeObservers: $response")
            when (response)
            {
                is Resource.Loading ->
                {
                    Log.i(TAG, "subscribeObservers: loading")
                }
                is Resource.Success<TodoResponse> ->
                {
                    Log.i(TAG, "subscribeObservers:Resource.Success-> response=${response.data}")
                }
                is Resource.Error ->
                {
                    handleApiError(response)
                }
            }
        }
    }


    override fun getFragmentBinding(inflater: LayoutInflater,
                                    container: ViewGroup?): FragmentHomeBinding =
            FragmentHomeBinding.inflate(inflater,
                                        container,
                                        false)
}
