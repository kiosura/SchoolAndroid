package com.example.schoolandroid.screens.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.schoolandroid.R
import com.example.schoolandroid.screens.BaseFragment


class about_school : BaseFragment(R.layout.fragment_about_school) {

    //private val viewModel: CourseViewModel by viewModels()

    companion object {
        @JvmStatic
        fun newInstance() = about_school()
    }
}