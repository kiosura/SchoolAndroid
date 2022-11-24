package com.example.schoolandroid.screens.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.schoolandroid.R
import com.example.schoolandroid.screens.BaseFragment
import com.example.schoolandroid.storage.PersistentStorage
import com.example.schoolandroid.storage.Storage


class profile: BaseFragment(R.layout.fragment_profile) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Storage.getUser()?.observe(viewLifecycleOwner) { user ->

        }
    }

    override fun onResume() {
        super.onResume()
        Toast.makeText(context, "${PersistentStorage.getProperty("name")}", Toast.LENGTH_LONG).show()
    }

    companion object {
        @JvmStatic
        fun newInstance() = profile()
    }
}