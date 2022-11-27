package com.example.schoolandroid.screens.main

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.schoolandroid.R
import com.example.schoolandroid.api.StorageViewModel
import com.example.schoolandroid.screens.BaseFragment
import com.example.schoolandroid.storage.PersistentStorage
import com.example.schoolandroid.storage.Storage


class profile(val isAuth : Boolean = false): BaseFragment(R.layout.fragment_profile) {

    private lateinit var storageViewModel: StorageViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        storageViewModel = ViewModelProvider(requireActivity()).get(StorageViewModel::class.java)
        if (isAuth) {
            storageViewModel.getCoursesWithLessons()
            storageViewModel.getMyCourses()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Storage.getUser().observe(viewLifecycleOwner) { user ->

        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = profile()
    }
}