package com.example.digitalbank.presenter.features.recharge

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
import com.example.digitalbank.data.model.Recharge
import com.example.digitalbank.databinding.FragmentRechardReceiptBinding
import com.example.digitalbank.util.GetMask
import com.example.digitalbank.util.StateView
import com.example.digitalbank.util.initToolbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RechardReceiptFragment : Fragment() {

    private var _binding: FragmentRechardReceiptBinding? = null
    private val binding get() = _binding!!

    private val rechargeReceiptViewModel: RechargeReceiptViewModel by viewModels()

    private val args: RechardReceiptFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRechardReceiptBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(binding.toolbar, homeAsUpEnable = args.homeAsUpEnable)

        getRecharge()
        initListener()
    }


    private fun getRecharge() {
        rechargeReceiptViewModel.getRecharge(args.idRecharge).observe(viewLifecycleOwner) { stateView ->
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
        binding.btnContinuarRecarga.setOnClickListener { findNavController().popBackStack() }
    }

    private fun configData(recharge: Recharge) {
        binding.textCodeTransactionRecarga.text = recharge.id
        binding.textDateTransactionRecarga.text =
            GetMask.getFormatedDate(recharge.date, GetMask.DAY_MONTH_YEAR_HOUR_MINUTE)
        binding.textAmountTransactionRecarga.text =
            getString(R.string.texto_formatado_valor, GetMask.getFormatedValue(recharge.amount))
        binding.textNumberRecarga.text = recharge.number
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}