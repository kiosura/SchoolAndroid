package com.example.schoolandroid.screens.main

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.example.schoolandroid.R
import com.example.schoolandroid.interfaces.Validator.Companion.validateRegistration
import com.example.schoolandroid.screens.BaseFragment
import com.example.schoolandroid.storage.Storage


class profile : BaseFragment(R.layout.fragment_profile) {

    private lateinit var loginPath : TextView
    private lateinit var registrationButton : TextView
    private lateinit var registrationLogin : EditText
    private lateinit var registrationPassword : EditText
    private lateinit var registrationPasswordComplete : EditText
    private lateinit var errorMessage : TextView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if ( Storage.getUser() != null) activity?.findViewById<ViewPager2>(R.id.mainActivityVp)!!.post(
                Runnable{ fragmentReplacer.replace(2, profile_face())
            })
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