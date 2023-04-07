package com.arif.generatepdffile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.arif.generatepdffile.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {
    private lateinit var binding: FragmentDashboardBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashboardBinding.inflate(inflater,container,false)
        binding.bkashBtn.setOnClickListener {
            goPayFragment("Bkash")
        }
        binding.nagadBtn.setOnClickListener {
            goPayFragment("Nagad")
        }
        binding.apiCheckBtn.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_dashboardFragment_to_apiFragment)
        }
        return binding.root
    }

    private fun goPayFragment(Type: String) {
        val bundle = Bundle()
        bundle.putString("Type", Type)
        Navigation.findNavController(requireView())
            .navigate(R.id.action_dashboardFragment_to_paymentFragment,bundle)
    }


}