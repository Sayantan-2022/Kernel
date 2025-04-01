package com.example.kernel.UI.fragments

import android.R
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment


class RegisterFragment : Fragment(com.example.kernel.R.layout.fragment_register) {

    private lateinit var radioGroup: RadioGroup
    private lateinit var btnContinue: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        radioGroup = view.findViewById(com.example.kernel.R.id.radioGroup)
        btnContinue = view.findViewById(com.example.kernel.R.id.btn_continue)

        radioGroup.setOnCheckedChangeListener { _: RadioGroup?, _: Int ->
            btnContinue.isEnabled =
                true
        }

        btnContinue.setOnClickListener {
            val selectedId = radioGroup.checkedRadioButtonId
            val selectedRadioButton: RadioButton = view.findViewById(selectedId)
            val selectedText = selectedRadioButton.text.toString()
            Toast.makeText(
                this@RegisterFragment.context,
                "Selected: $selectedText",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}