package com.example.digitalbank.presenter.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.digitalbank.R
import com.example.digitalbank.data.model.User
import com.example.digitalbank.databinding.FragmentHomeBinding
import com.example.digitalbank.databinding.FragmentProfileBinding
import com.example.digitalbank.util.FirebaseHelper
import com.example.digitalbank.util.StateView
import com.example.digitalbank.util.initToolbar
import com.example.digitalbank.util.showBottomSheet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val profileViewModel:ProfileViewModel by viewModels()

    private var usuario : User? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(binding.toolbar)
        getProfile()
    }

    private fun getProfile(){
        profileViewModel.getProfile().observe(viewLifecycleOwner){ stateView ->
            when(stateView){
                is StateView.Loading -> {
                    binding.progressBar.isVisible = true
                }
                is StateView.Sucess -> {
                    binding.progressBar.isVisible = false
                    stateView.data?.let { usuario = it }
                    configData()

                }
                is StateView.Error -> {
                    binding.progressBar.isVisible = false
                    showBottomSheet(message = getString(FirebaseHelper.validErrors(stateView.message ?: "")))
                }
            }
        }
    }

    private fun configData(){
        binding.editName.setText(usuario?.name)
        binding.editPhone.setText(usuario?.celular)
        binding.editEmail.setText(usuario?.email)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}