package com.example.schoolandroid.screens.main

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.schoolandroid.R
import com.example.schoolandroid.api.RegAuthViewModel
import com.example.schoolandroid.screens.BaseFragment
import com.example.schoolandroid.storage.Storage
import com.example.schoolandroid.interfaces.Validator.Companion.registrationValidation

class regAuth : BaseFragment(R.layout.fragment_reg_auth) {

    private lateinit var loginPath : TextView
    private lateinit var registrationButton : TextView
    private lateinit var registrationLogin : EditText
    private lateinit var registrationPassword : EditText
    private lateinit var registrationPasswordComplete : EditText
    private lateinit var loginError : TextView
    private lateinit var passwordError : TextView
    private lateinit var regAuthViewModel : RegAuthViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        regAuthViewModel = ViewModelProvider(this).get(RegAuthViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginPath = view.findViewById(R.id.loginPath)
        registrationButton = view.findViewById(R.id.registrationButton)
        registrationLogin = view.findViewById(R.id.registrationLogin)
        registrationPassword = view.findViewById(R.id.registrationPassword)
        registrationPasswordComplete = view.findViewById(R.id.registrationPasswordComplete)
        loginError = view.findViewById(R.id.loginError)
        passwordError = view.findViewById(R.id.passwordError)

        loginReplace()
        registration()

    }

    fun loginReplace() {
        loginPath.setOnClickListener {
            loginPath.text = "Зарегистрироваться"
            registrationButton.text = "Войти"
            registrationPasswordComplete.isVisible = false
            login()
            registrationReplace()
        }
    }

    fun registrationReplace() {
        loginPath.setOnClickListener {
            loginPath.text = "У меня уже есть аккаунт"
            registrationButton.text = "Зарегистрироваться"
            registrationPasswordComplete.isVisible = true
            registration()
            loginReplace()
        }
    }

    fun login() {
        registrationButton.setOnClickListener {
            regAuthViewModel.postAuthentification(
                fragmentReplacer,
                registrationLogin.text.toString(),
                registrationPassword.text.toString()
            )

            Storage.getUser()?.observe(viewLifecycleOwner){user ->
                Toast.makeText(context,"${user.error_message}" , Toast.LENGTH_LONG).show()
            }
        }
    }

    //
    @Suppress("DEPRECATION")
    fun registration() {
        registrationButton.setOnClickListener {
            val (loginErrorReturned, passwordErrorReturned, result) = registrationValidation(
                    registrationLogin.text.toString(),
                    registrationPassword.text.toString(),
                    registrationPasswordComplete.text.toString()
            )
            with(loginError) {
                text = loginErrorReturned
                setTextColor(resources.getColor(R.color.teal_200))
            }
            with(passwordError) {
                text = passwordErrorReturned
                setTextColor(resources.getColor(R.color.purple_700))
            }
            if (result) {
                regAuthViewModel.postRegistration(
                    fragmentReplacer,
                    registrationLogin.text.toString(),
                    registrationPassword.text.toString()
                )
            }

            Storage.getUser()?.observe(viewLifecycleOwner){user ->
                Toast.makeText(context,"${user.error_message}" , Toast.LENGTH_LONG).show()
            }

        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = regAuth()
    }
}