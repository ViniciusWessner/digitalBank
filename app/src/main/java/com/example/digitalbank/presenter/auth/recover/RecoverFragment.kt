package com.example.digitalbank.presenter.auth.recover

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.digitalbank.R
import com.example.digitalbank.databinding.FragmentRecoverBinding
import com.example.digitalbank.util.BaseFragment
import com.example.digitalbank.util.FirebaseHelper
import com.example.digitalbank.util.StateView
import com.example.digitalbank.util.initToolbar
import com.example.digitalbank.util.showBottomSheet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecoverFragment : BaseFragment() {

    private var _binding: FragmentRecoverBinding? = null
    private val binding get() = _binding!!

    private val recoverViewModel: RecoverViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecoverBinding.inflate(inflater, container, false)
         return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(binding.toolbar)
        initListeners()
    }

    private fun initListeners() {
        binding.btnSendLink.setOnClickListener {
            validadeData()
        }
    }

    private fun validadeData() {
        val email = binding.editEmail.text.toString().trim()

        if (email.isNotEmpty()){
            hideKeyboard()
            recouverAccount(email)
        }else{
            showBottomSheet(message = getString(R.string.preenchaEmail))
        }
    }

    private fun recouverAccount(email: String){

        recoverViewModel.recover(email).observe(viewLifecycleOwner){ stateView ->
            when(stateView){
                is StateView.Loading -> {
                    binding.progressBar.isVisible = true
                }
                is StateView.Sucess -> {
                    binding.progressBar.isVisible = false
                    showBottomSheet(message = getString((R.string.verifiqueEmail)))
                    findNavController().popBackStack()
                }
                is StateView.Error -> {
                    binding.progressBar.isVisible = false
                    showBottomSheet(message = getString(FirebaseHelper.validErrors(stateView.message ?: "")))
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}