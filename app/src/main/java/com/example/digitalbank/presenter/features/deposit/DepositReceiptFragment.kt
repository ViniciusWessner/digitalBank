package com.example.digitalbank.presenter.features.deposit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.digitalbank.R
import com.example.digitalbank.data.model.Deposit
import com.example.digitalbank.databinding.FragmentDepositReceiptBinding
import com.example.digitalbank.util.GetMask
import com.example.digitalbank.util.StateView
import com.example.digitalbank.util.initToolbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DepositReceiptFragment : Fragment() {

    private var _binding: FragmentDepositReceiptBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DepositReceiptViewModel by viewModels()
    private val args: DepositReceiptFragmentArgs by navArgs()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDepositReceiptBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(binding.toolbar, args.homeAsUpEnable)
        initListener()
        getDeposit()
    }

    private fun getDeposit() {
        viewModel.getDeposit(args.idDeposit).observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {

                }

                is StateView.Sucess -> {
                    stateView.data?.let { configData(it) }
                }

                is StateView.Error -> {
                    Toast.makeText(requireContext(), "ocorreu um erro", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
            }
        }
    }

    private fun initListener() {
        binding.btnContinuar.setOnClickListener { findNavController().popBackStack() }
    }

    private fun configData(deposit: Deposit) {
        binding.textCodeTransaction.text = deposit.id
        binding.textDateTransaction.text =
            GetMask.getFormatedDate(deposit.date, GetMask.DAY_MONTH_YEAR_HOUR_MINUTE)
        binding.textAmountTransaction.text =
            getString(R.string.texto_formatado_valor, GetMask.getFormatedValue(deposit.amount))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}