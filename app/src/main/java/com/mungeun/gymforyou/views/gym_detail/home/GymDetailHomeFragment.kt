package com.mungeun.gymforyou.views.gym_detail.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mungeun.gymforyou.databinding.FragmentGymDetailHomeBinding


class GymDetailHomeFragment : Fragment() {


    private lateinit var mBinding : FragmentGymDetailHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentGymDetailHomeBinding.inflate(inflater,container,false).apply {
            lifecycleOwner = this@GymDetailHomeFragment
        }
        arguments?.takeIf { it.containsKey("gymDescription") }?.apply {
            mBinding.tvExplanation.text = getString("gymDescription").toString()
        }

        return mBinding.root
    }



}