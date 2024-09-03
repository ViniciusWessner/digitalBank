package com.example.digitalbank.presenter.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.digitalbank.R
import com.example.digitalbank.data.model.Wallet
import com.example.digitalbank.databinding.FragmentHomeBinding
import com.example.digitalbank.util.GetMask
import com.example.digitalbank.util.StateView
import com.example.digitalbank.util.showBottomSheet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getWallet()

        initListenear()
    }

    private fun getWallet(){
        homeViewModel.getWallet().observe(viewLifecycleOwner) { stateView ->
            when(stateView) {
                is StateView.Loading -> {

                }

                is StateView.Error -> {
                    showBottomSheet(message = stateView.message)
                }
                is StateView.Sucess -> {
                    stateView.data?.let { showBalance(it) }
                }
            }
        }
    }

    private fun showBalance(wallet: Wallet) {
        binding.textBalance.text = getString(R.string.texto_formatado_valor, GetMask.getFormatedValue(wallet.balance))
    }

    private fun initListenear(){ //ouvinte dos componentes
        binding.cardDeposit.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_depositFormFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}