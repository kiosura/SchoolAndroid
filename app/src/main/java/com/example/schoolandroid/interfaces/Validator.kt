package com.example.schoolandroid.interfaces

import android.annotation.SuppressLint
import android.widget.TextView
import com.example.schoolandroid.R

interface Validator {
    companion object {
        fun validateRegistration(log : String, password : String, passwordComplete : String) : Pair<String, Int> {
            var text : String
            var color : Int
            if (validLog(log) && validPassword(password) && (password == passwordComplete))  {
                text = "complete"
                color = R.color.teal_200
            }
            else {
                text = "wrong input"
                color = R.color.black
            }
            return Pair(text, color)
        }

        // проверка логина (почта/номер телефона)
        fun validLog(log: String) : Boolean{
            var isItTrue : Boolean = false
            if (log.length > 6) isItTrue = true
            return isItTrue
        }

        // проверка валидации пароля
        fun validPassword(password: String) : Boolean{
            var isItTrue : Boolean = false
            if (password.length > 6) isItTrue = true
            return isItTrue
        }
    }
}
