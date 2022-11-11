package com.example.schoolandroid.screens.course

import android.app.ActivityManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.schoolandroid.R
import com.example.schoolandroid.screens.BaseFragment


class task : BaseFragment(R.layout.task_view) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val backtolesson = activity?.findViewById<Button>(R.id.backtoMain)
        backtolesson?.setOnClickListener {
            fragmentReplacer.replaceDef(1)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = task()
    }
}