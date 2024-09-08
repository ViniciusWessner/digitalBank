package com.example.digitalbank.presenter.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.digitalbank.R
import com.example.digitalbank.data.enum.TransactionType
import com.example.digitalbank.data.model.Transaction
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
    private lateinit var adapterTransaction: TransactionAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configRecyclerView() //inicia primeiro o adapter e depois recupera as transacoes

        getTransactions()

        initListenear()
    }

    private fun configRecyclerView(){
        adapterTransaction = TransactionAdapter(){ tansaction ->


        }

        with(binding.rvTransactions) { //usamos with para pegar o contexto e nao ficar repetindo
            setHasFixedSize(true)
            adapter = adapterTransaction
        }
    }

    private fun getTransactions(){
        homeViewModel.getTransactions().observe(viewLifecycleOwner) { stateView ->
            when(stateView) {
                is StateView.Loading -> {
                    binding.progressbar.isVisible = true
                }

                is StateView.Error -> {
                    binding.progressbar.isVisible = false
                    showBottomSheet(message = stateView.message)
                }
                is StateView.Sucess -> {
                    binding.progressbar.isVisible = false
                    showBalance(stateView.data ?: emptyList())
                    adapterTransaction.submitList(stateView.data?.reversed()?.take(6))

                }
            }
        }
    }

    private fun showBalance(transactions: List<Transaction>) {
        var cashIn = 0f
        var cashOut = 0f

        transactions.forEach{ transaction ->
            if (transaction.type == TransactionType.CASH_IN){
                cashIn += transaction.amount
            } else {
                cashOut += transaction.amount
            }
        }


        binding.textBalance.text = getString(R.string.texto_formatado_valor, GetMask.getFormatedValue(cashIn - cashOut))
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