package com.example.kernel.UI.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.kernel.R

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private lateinit var radioGroup: RadioGroup
    private lateinit var btnRegister: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        radioGroup = view.findViewById(R.id.radioGroup)
        btnRegister = view.findViewById(R.id.btnRegister)

        radioGroup.setOnCheckedChangeListener { _: RadioGroup?, _: Int ->
            btnRegister.isEnabled =
                true
        }

        btnRegister.setOnClickListener {
            val selectedId = radioGroup.checkedRadioButtonId
            val selectedRadioButton: RadioButton? = view.findViewById(selectedId)
            if(selectedRadioButton!=null) {
                val selectedText = selectedRadioButton.text.toString()
                Toast.makeText(
                    this@SignUpFragment.context,
                    "Selected: $selectedText",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}