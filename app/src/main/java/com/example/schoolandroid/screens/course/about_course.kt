package com.example.schoolandroid.screens.course

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import com.example.schoolandroid.R
import com.example.schoolandroid.api.FirstApi.Companion.apiBase
import com.example.schoolandroid.screens.BaseFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class about_course : BaseFragment(R.layout.fragment_about_course) {

    companion object {
        @JvmStatic
        fun newInstance() = about_course()
    }
}