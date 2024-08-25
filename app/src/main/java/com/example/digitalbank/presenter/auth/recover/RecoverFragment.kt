package com.example.digitalbank.presenter.auth.recover

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.digitalbank.R
import com.example.digitalbank.databinding.FragmentRecoverBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecoverFragment : Fragment() {

    private var _binding: FragmentRecoverBinding? = null
    private val binding get() = _binding!!

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
            Toast.makeText(requireContext(), "Email enviado", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(requireContext(), "Digite seu e-mail correto", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}