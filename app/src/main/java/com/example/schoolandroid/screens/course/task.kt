package com.example.schoolandroid.screens.course

import android.app.ActivityManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.schoolandroid.R
import com.example.schoolandroid.screens.BaseFragment


class task : BaseFragment(R.layout.task_view) {

    companion object {
        @JvmStatic
        fun newInstance() = task()
    }
}