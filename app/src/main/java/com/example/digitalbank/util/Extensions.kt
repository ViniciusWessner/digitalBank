package com.example.digitalbank.util

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.motion.widget.MotionScene.Transition.TransitionOnClick
import androidx.fragment.app.Fragment
import com.example.digitalbank.R
import com.example.digitalbank.databinding.LayoutBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

//inicializacao da tooolbar
fun Fragment.initToolbar(toolbar: Toolbar, homeAsUpEnable: Boolean = true){
    (activity as AppCompatActivity).setSupportActionBar(toolbar)
    (activity as AppCompatActivity).title = ""
    (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
    (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(homeAsUpEnable)
    toolbar.setNavigationOnClickListener { activity?.onBackPressedDispatcher?.onBackPressed() }
}

fun Fragment.showBottomSheet(
    title: Int? = null,
    message: String,
    titleButton: Int? = null,
    onClick: () -> Unit = {}
) {
    val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialog)
    val bottomSheetBinding: LayoutBottomSheetBinding =
        LayoutBottomSheetBinding.inflate(layoutInflater, null, false)

    bottomSheetBinding.textTitle.text = getString(title ?: R.string.title_bottom_sheet)
    bottomSheetBinding.textMessage.text = message
    bottomSheetBinding.btnOk.text = getString(title ?: R.string.button_bottom_sheet)

    bottomSheetBinding.btnOk.setOnClickListener {
        bottomSheetDialog.dismiss()
        onClick()
    }

    bottomSheetDialog.setContentView(bottomSheetBinding.root)
    bottomSheetDialog.show()
}