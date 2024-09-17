package com.example.digitalbank.presenter.features.recharge

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels

import com.example.digitalbank.R
import com.example.digitalbank.data.enum.TransactionOperation
import com.example.digitalbank.data.enum.TransactionType

import com.example.digitalbank.data.model.Recharge
import com.example.digitalbank.data.model.Transaction

import com.example.digitalbank.databinding.FragmentRechargeFormBinding
import com.example.digitalbank.util.BaseFragment
import com.example.digitalbank.util.StateView
import com.example.digitalbank.util.initToolbar
import com.example.digitalbank.util.showBottomSheet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RechargeFormFragment : BaseFragment() {

    private var _binding: FragmentRechargeFormBinding? = null
    private val binding get() = _binding!!

    private val rechargeViewModel: RechargeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRechargeFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(binding.toolbar)
        initListener()
    }


    private fun validadeData() {
        val amount = binding.editAmount.text.toString().trim()
        val celular = binding.editNumber.unMaskedText //sem a mascara

        if (amount.isNotEmpty()) {
            if (celular?.isNotEmpty() == true) {
                if (celular.length == 11) {

                    hideKeyboard()
                    val recharge = Recharge(
                        amount = amount.toFloat(),
                        number = celular
                    )
                    saveRecharge(recharge)
                    binding.progressBar.isVisible = true

                } else {
                    showBottomSheet(message = getString(R.string.celular_incorreto))
                }
            } else {
                showBottomSheet(message = getString(R.string.celular_empty))
            }
        } else {
            showBottomSheet(message = getString(R.string.valor_da_recarga))
        }

    }


    private fun initListener() {
        binding.btnRecarregar.setOnClickListener { validadeData() }
    }

    private fun saveRecharge(recharge: Recharge) {
        rechargeViewModel.saveRecharge(recharge).observe(viewLifecycleOwner) { stateView ->
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

    private fun saveTransaction(recharge: Recharge) {

        val transaction = Transaction(
            id = recharge.id,
            operation = TransactionOperation.RECHARGE,
            date = recharge.date,
            amount = recharge.amount,
            type = TransactionType.CASH_OUT
        )

        rechargeViewModel.saveTransaction(transaction).observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {

                }

                is StateView.Sucess -> {
                    showBottomSheet(message = "Informação enviada ao firebase")
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