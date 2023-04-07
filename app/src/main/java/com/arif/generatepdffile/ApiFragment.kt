package com.arif.generatepdffile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.arif.generatepdffile.adapter.CatagoryAdapter
import com.arif.generatepdffile.databinding.FragmentApiBinding
import com.arif.generatepdffile.viewmodel.ApiViewModel

class ApiFragment : Fragment() {

    private lateinit var binding: FragmentApiBinding
    private val  apiViewModel: ApiViewModel by activityViewModels()
    private val nameList = ArrayList<String>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val adapter = CatagoryAdapter()
        binding = FragmentApiBinding.inflate(inflater,container,false)
        apiViewModel.fetchCategory("benefit")
        apiViewModel.fetchCategory1("benefit","48")
        apiViewModel.categoryData.observe(viewLifecycleOwner){
            binding.categoryRvId.layoutManager = GridLayoutManager(requireActivity(),1)
            binding.categoryRvId.adapter = adapter
            adapter.submitList(it.categories)
            Log.d("checkApi1", "onCreateView:${it.categories[0].name} ")
            Log.d("checkApi2", "onCreateView:${it.categories[0].childCount} ")
        }
        apiViewModel.categoryData1.observe(viewLifecycleOwner){
            Log.d("checkApi3", "onCreateView:${it.categories[0].name} ")
        }
        return binding.root
    }

}