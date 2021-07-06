package dan.nr.myapplication.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import dan.nr.myapplication.base.BaseFragment
import dan.nr.myapplication.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>()
{



    override fun getFragmentBinding(inflater: LayoutInflater,
                                    container: ViewGroup?): FragmentHomeBinding =
            FragmentHomeBinding.inflate(inflater,
                                        container,
                                        false)
}
