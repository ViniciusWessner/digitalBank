package com.example.digitalbank.presenter.features.extract

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.digitalbank.R
import com.example.digitalbank.data.enum.TransactionOperation
import com.example.digitalbank.databinding.FragmentDepositFormBinding
import com.example.digitalbank.databinding.FragmentExtractBinding
import com.example.digitalbank.presenter.home.HomeFragmentDirections
import com.example.digitalbank.presenter.home.TransactionAdapter
import com.example.digitalbank.util.StateView
import com.example.digitalbank.util.initToolbar
import com.example.digitalbank.util.showBottomSheet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExtractFragment : Fragment() {

    private var _binding: FragmentExtractBinding? = null
    private val binding get() = _binding!!

    private val extractViewModel: ExtractViewModel by viewModels()
    private lateinit var adapterTransaction: TransactionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExtractBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(binding.toolbar)

        configRecyclerView()

        getTransactions()
    }


    private fun configRecyclerView() {
        adapterTransaction = TransactionAdapter(requireContext()) { transaction ->
            when (transaction.operation) {
                TransactionOperation.DEPOSIT -> {
                    val action = ExtractFragmentDirections
                        .actionExtractFragmentToDepositReceiptFragment(transaction.id, true)

                    findNavController().navigate(action)
                }
                else -> {
                }
            }
        }

        binding.rvTransactions.apply {
            setHasFixedSize(true)
            adapter = adapterTransaction
        }
    }

    private fun getTransactions() {
        extractViewModel.getTransactions().observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {
                    binding.progressbar.isVisible = true
                }

                is StateView.Error -> {
                    binding.progressbar.isVisible = false
                    showBottomSheet(message = stateView.message)
                }

                is StateView.Sucess -> {
                    binding.progressbar.isVisible = false
                    adapterTransaction.submitList(stateView.data?.reversed()?.take(6))

                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}