package com.example.schoolandroid.interfaces

import android.annotation.SuppressLint
import android.text.TextUtils
import android.widget.TextView
import com.example.schoolandroid.R

interface Validator {
    companion object {
//        fun registrationValidation(log : String, password : String, passwordComplete : String) : Pair<String, Int> {
//            var text : String
//            var color : Int
//            if (loginValidation(log) && passwordValidation(password) && (password == passwordComplete)) {
//                text = "complete"
//                color = R.color.teal_200
//            }
//            else {
//                text = "wrong input"
//                color = R.color.black
//            }
//            return Pair(text, color)
//        }
        fun registrationValidation(login : String, password : String, passwordComplete: String) : Triple<String, String, Boolean> {
            var loginError : String = ""
            var passwordError : String = ""
            var result : Boolean = true
            if (!loginValidation(login)) {
                loginError = "Некорректная почта или телефон"
                result = false
            }
            if (password != passwordComplete) {
                passwordError = "Пароли не совпадают"
                result = false
            }
            if (!passwordLengthValidation(password)) {
                passwordError = "Пароль короче 8 символов"
                result = false
            }
            return Triple(loginError, passwordError, result)
        }

        // email or phone number validation
        fun loginValidation(login: String) : Boolean {

            // email validation
            if (login.contains("@")) {
                if (TextUtils.isEmpty(login)) {
                    return false
                } else {
                    return android.util.Patterns.EMAIL_ADDRESS.matcher(login).matches()
                }
            }

            // phone validation
            else {
                when (login.length) {
                    11 -> return login[0] == '8'
                    12 -> return login[0] == '+' && login[1] == '7'
                    else -> return false
                }
            }
        }

        // password length validation
        fun passwordLengthValidation(password: String) : Boolean {
            return password.length >= 8
        }
    }
}
