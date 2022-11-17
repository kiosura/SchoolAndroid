package com.example.schoolandroid.screens.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.schoolandroid.R


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