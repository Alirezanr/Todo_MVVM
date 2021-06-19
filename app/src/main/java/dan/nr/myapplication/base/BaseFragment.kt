package dan.nr.myapplication.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import dan.nr.myapplication.network.RemoteDataSource
import javax.inject.Inject

abstract class BaseFragment<VM : BaseViewModel, B : ViewBinding, R : BaseRepository> : Fragment()
{
    @Inject
    lateinit var remoteDataSource: RemoteDataSource

    protected lateinit var binding: B
    protected lateinit var viewModel: VM

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        return super.onCreateView(inflater, container, savedInstanceState)

    }

    abstract fun getViewModel(): Class<VM>
    abstract fun getFragmentBinding(inflater: LayoutInflater,container: ViewGroup?):B
    abstract fun getFragmentRepository():R
}