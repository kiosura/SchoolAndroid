package com.example.schoolandroid.screens.main

import android.os.Bundle
import android.view.View
import com.example.schoolandroid.R
import com.example.schoolandroid.screens.BaseFragment


class profile_face: BaseFragment(R.layout.fragment_profile_face) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    companion object {
        @JvmStatic
        fun newInstance() = profile_face()
    }
}