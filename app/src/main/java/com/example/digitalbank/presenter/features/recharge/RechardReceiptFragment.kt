package com.example.digitalbank.presenter.features.recharge

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.digitalbank.R
import com.example.digitalbank.databinding.FragmentDepositFormBinding
import com.example.digitalbank.databinding.FragmentRechardReceiptBinding
import com.example.digitalbank.util.initToolbar

class RechardReceiptFragment : Fragment() {

    private var _binding: FragmentRechardReceiptBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRechardReceiptBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(binding.toolbar)
    }

}