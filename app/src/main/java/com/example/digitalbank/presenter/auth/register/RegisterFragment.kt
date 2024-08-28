package com.example.digitalbank.presenter.auth.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.example.digitalbank.R
import com.example.digitalbank.data.model.User
import com.example.digitalbank.databinding.FragmentRegisterBinding
import com.example.digitalbank.util.StateView
import com.example.digitalbank.util.initToolbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {


    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val registerViewModel: RegisterViewModel by viewModels()

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
        val celular = binding.editPhone.text.toString().trim()
        val senha = binding.editSenha.text.toString().trim()
        val confirmSenha = binding.editConfirmSenha.text.toString().trim()


        if (name.isNotEmpty()){
            if (email.isNotEmpty()){
                if (celular.isNotEmpty()){
                    if (senha == confirmSenha && senha.isNotEmpty() && confirmSenha.isNotEmpty()){
                        val user = User(name, email, celular, senha)
                        registerUser(user)

                        binding.progressBar.isVisible = true

                    } else {
                        Toast.makeText(requireContext(), "Verifique sua senha", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(requireContext(), "Digite seu celular", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(requireContext(), "Digite uma senha", Toast.LENGTH_SHORT).show()
            }
        } else{
            Toast.makeText(requireContext(), "Digite seu nome", Toast.LENGTH_SHORT).show()
        }

    }

    private fun registerUser(user: User){

        registerViewModel.register(user).observe(viewLifecycleOwner){ stateView ->
            when(stateView){
                is StateView.Loading -> {
                    binding.progressBar.isVisible = true
                }
                is StateView.Sucess -> {
                    binding.progressBar.isVisible = false
                    Toast.makeText(requireContext(), "Cadastro realizado", Toast.LENGTH_SHORT).show()
                }
                is StateView.Error -> {
                    binding.progressBar.isVisible = false
                    Toast.makeText(requireContext(), stateView.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}