package com.example.schoolandroid.interfaces

import com.example.schoolandroid.screens.BaseFragment

interface FragmentReplacer {
    fun replace(position: Int, newFragment: BaseFragment, isNotify: Boolean = true)
    fun replaceDef(position: Int, isNotify: Boolean = true) : BaseFragment
}