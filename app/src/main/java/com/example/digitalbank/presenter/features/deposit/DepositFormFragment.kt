package com.example.digitalbank.presenter.features.deposit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.digitalbank.data.enum.TransactionOperation
import com.example.digitalbank.data.enum.TransactionType
import com.example.digitalbank.data.model.Deposit
import com.example.digitalbank.data.model.Transaction
import com.example.digitalbank.databinding.FragmentDepositFormBinding
import com.example.digitalbank.util.BaseFragment
import com.example.digitalbank.util.StateView
import com.example.digitalbank.util.initToolbar
import com.example.digitalbank.util.showBottomSheet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DepositFormFragment : BaseFragment() {

    private var _binding: FragmentDepositFormBinding? = null
    private val binding get() = _binding!!

    private val depositViewModel: DepositViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDepositFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(binding.toolbar)

        initListener()
    }

    private fun initListener() {
        binding.btnContinue.setOnClickListener { validateDeposit() }
    }

    private fun validateDeposit() {
        val amount = binding.editAmount.text.toString().trim()

        if (amount.isNotEmpty()) {
            val deposit = Deposit(amount = amount.toFloat())
            hideKeyboard()
            saveDeposit(deposit)
        } else {
            showBottomSheet(message = "Insira um valor de deposito")
        }
    }

    private fun saveDeposit(deposit: Deposit) {
        depositViewModel.saveDeposit(deposit).observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {
                    binding.progressBar.isVisible = true
                }

                is StateView.Sucess -> {
                    stateView.data?.let { saveTransaction(it) }
                }

                is StateView.Error -> {
                    binding.progressBar.isVisible = false
                    showBottomSheet(message = stateView.message)
                }
            }
        }
    }

    private fun saveTransaction(deposit: Deposit) {

        val transaction = Transaction(
            id = deposit.id,
            operation = TransactionOperation.DEPOSIT,
            date = deposit.date,
            amount = deposit.amount,
            type = TransactionType.CASH_IN
        )

        depositViewModel.saveTransaction(transaction).observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {

                }

                is StateView.Sucess -> {
                    val action = DepositFormFragmentDirections
                        .actionDepositFormFragmentToDepositReceiptFragment(deposit.id.toString(), false)

                    findNavController().navigate(action)
                }

                is StateView.Error -> {
                    binding.progressBar.isVisible = false
                    showBottomSheet(message = stateView.message)
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}