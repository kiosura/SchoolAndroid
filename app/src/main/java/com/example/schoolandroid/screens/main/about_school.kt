package com.example.schoolandroid.screens.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import com.example.schoolandroid.R
import com.example.schoolandroid.api.CourseViewModel
import com.example.schoolandroid.api.RetrofitApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class about_school : Fragment() {

    //private val viewModel: CourseViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view : View = inflater.inflate(R.layout.fragment_about_school, container, false)

        //viewModel.fetchCourses(this.activity?.application as RetrofitApi)


        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = about_school()
    }
}