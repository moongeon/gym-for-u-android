package com.mungeun.gymforyou.views.chatting


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mungeun.gymforyou.R
import com.mungeun.gymforyou.adapters.CattingAdapter
import com.mungeun.gymforyou.data.Chat
import com.mungeun.gymforyou.databinding.FragmentChattingBinding
import com.mungeun.gymforyou.utilities.EventObserver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChattingFragment : Fragment() {

    private val viewModel : ChattingViewModel by viewModels()
    private lateinit var mBinding: FragmentChattingBinding

    private val dataSet = arrayListOf<Chat>().apply {
        add(Chat("문근1", "하이1",""))
        add(Chat("문근2", "하이2",""))
        add(Chat("문근3", "하이3",""))

    }
    private val cattingAdapter: CattingAdapter by lazy {
        CattingAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentChattingBinding.inflate(inflater, container, false)
        binding.chattingList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = cattingAdapter
        }
        mBinding = binding
        mBinding.vm = viewModel
        cattingAdapter.submitList(dataSet)


        with(viewModel){
            newMessageCall.observe(viewLifecycleOwner,EventObserver{
                if(it){
                    findNavController().navigate(R.id.newChattingFragment)
                }
            })





        }


        return mBinding.root
    }
}