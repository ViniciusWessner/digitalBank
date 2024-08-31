package com.example.digitalbank.presenter.auth.register

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
import com.example.digitalbank.data.model.User
import com.example.digitalbank.databinding.FragmentRegisterBinding
import com.example.digitalbank.presenter.profile.ProfileViewModel
import com.example.digitalbank.util.FirebaseHelper
import com.example.digitalbank.util.StateView
import com.example.digitalbank.util.initToolbar
import com.example.digitalbank.util.showBottomSheet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {


    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val registerViewModel: RegisterViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(binding.toolbar)
        initListeners()
    }

    private fun initListeners() {
        binding.btnRegister.setOnClickListener {
            validadeDate()
        }
    }

    private fun validadeDate() {
        val name = binding.editName.text.toString().trim()
        val email = binding.editEmail.text.toString().trim()
        val celular = binding.editPhone.unMaskedText //sem a mascara
        val senha = binding.editSenha.text.toString().trim()
        val confirmSenha = binding.editConfirmSenha.text.toString().trim()


        if (name.isNotEmpty()){
            if (email.isNotEmpty()){
                if (celular?.isNotEmpty() == true){
                    if (celular.length == 11) {
                        if (senha == confirmSenha && senha.isNotEmpty() && confirmSenha.isNotEmpty()){
                            registerUser(name, email, celular, senha)
                            binding.progressBar.isVisible = true
                        } else {
                            showBottomSheet(message = getString(R.string.senhas_iguais))
                        }
                    } else{
                        showBottomSheet(message = getString(R.string.celular_incorreto))
                    }
                }else{
                    showBottomSheet(message = getString(R.string.celular_empty))
                }
            }else{
                showBottomSheet(message = getString(R.string.email_empty))
            }
        } else{
            showBottomSheet(message = getString(R.string.name_empty))
        }

    }

    private fun registerUser(nome: String,email: String, celular: String, senha: String){
        registerViewModel.register(nome, email, celular, senha).observe(viewLifecycleOwner){ stateView ->
            when(stateView){
                is StateView.Loading -> {
                    binding.progressBar.isVisible = true
                }
                is StateView.Sucess -> {
                    stateView.data?.let { saveProfile(it) }

                }
                is StateView.Error -> {
                    binding.progressBar.isVisible = false
                    showBottomSheet(message = getString(FirebaseHelper.validErrors(stateView.message ?: "")))
                }
            }
        }
    }

    private fun saveProfile(user: User){
        profileViewModel.saveProfile(user).observe(viewLifecycleOwner){ stateView ->
            when(stateView){
                is StateView.Loading -> {

                }
                is StateView.Sucess -> {
                    binding.progressBar.isVisible = false
                    findNavController().navigate(R.id.action_global_homeFragment)
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