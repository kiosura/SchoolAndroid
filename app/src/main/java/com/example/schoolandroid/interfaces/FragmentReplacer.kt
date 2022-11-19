package com.example.schoolandroid.interfaces

import com.example.schoolandroid.screens.BaseFragment

interface FragmentReplacer {

    fun replace(position: Int, newFragment: BaseFragment, isNotify: Boolean = true) {
        newFragment.setPageInfo(
            pagePos = position,
            fragmentReplacer = this
        )
    }


    fun replaceDefault(position: Int, isNotify: Boolean = true, newFrag : List<BaseFragment> = listOf()) : BaseFragment {
        val fragment : BaseFragment = when (position) {
            0 -> newFrag[0]
            1 -> newFrag[1]
            2 -> newFrag[2]
            else -> throw IllegalStateException()
        }
        fragment.setPageInfo(
            pagePos = position,
            fragmentReplacer = this
        )

        replace(position, fragment, isNotify)

        return fragment
    }
}