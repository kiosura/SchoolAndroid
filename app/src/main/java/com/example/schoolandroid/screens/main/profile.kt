package com.example.schoolandroid.screens.main

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.example.schoolandroid.R
import com.example.schoolandroid.interfaces.Validator.Companion.validateRegistration


class profile : Fragment() {

    private lateinit var loginPath : TextView
    private lateinit var registrationButton : TextView
    private lateinit var registrationLogin : EditText
    private lateinit var registrationPassword : EditText
    private lateinit var registrationPasswordComplete : EditText
    private lateinit var errorMessage : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginPath = view.findViewById(R.id.loginPath)
        registrationButton = view.findViewById(R.id.registrationButton)
        registrationLogin = view.findViewById(R.id.registrationLogin)
        registrationPassword = view.findViewById(R.id.registrationPassword)
        registrationPasswordComplete = view.findViewById(R.id.registrationPasswordComplete)
        errorMessage = view.findViewById(R.id.errorOccured)

        loginReplace()
        registration()
    }

    fun loginReplace() {
        loginPath.setOnClickListener {

        }
    }

    fun registration() {
        println(1)
        registrationButton.setOnClickListener {
            val (textRes, colorRes) = validateRegistration(registrationLogin.text.toString(),
                registrationPassword.text.toString(),
                registrationPasswordComplete.text.toString())
            with(errorMessage){
                text = textRes
                setTextColor(resources.getColor(colorRes))
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = profile()
    }
}