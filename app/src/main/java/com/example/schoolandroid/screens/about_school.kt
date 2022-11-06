package com.example.schoolandroid.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import com.example.schoolandroid.R
import kotlinx.coroutines.*
import com.example.schoolandroid.api.FirstApi.Companion.apiBase


class about_school : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_about_school, container, false)
    }

    override fun onStart() {
        super.onStart()

        val mainText = activity?.findViewById<TextView>(R.id.mainText)

        lifecycleScope.launch {
            whenStarted {
                mainText?.text = withContext(Dispatchers.IO) {
                    apiBase()
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = about_school()
    }
}