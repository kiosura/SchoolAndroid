package com.example.schoolandroid.adapter

import androidx.collection.ArrayMap
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.schoolandroid.interfaces.FragmentReplacer
import com.example.schoolandroid.screens.BaseFragment
import com.example.schoolandroid.screens.course.about_course
import com.example.schoolandroid.screens.course.chats
import com.example.schoolandroid.screens.course.lesson

class CoursePageAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa),
    FragmentReplacer {

    val mapOfFragment : ArrayMap<Int, BaseFragment> = ArrayMap<Int, BaseFragment>()

    companion object {
        private const val PAGE_COUNT = 3
    }


    override fun replace(position: Int, newFragment: BaseFragment, isNotify: Boolean) {
        mapOfFragment[position] = newFragment
        if (isNotify)
            notifyItemChanged(position)
    }

    override fun replaceDef(position: Int, isNotify: Boolean): BaseFragment {
        val fragment : BaseFragment = when (position) {
            0 -> about_course()
            1 -> lesson()
            2 -> chats()
            else -> throw IllegalStateException()
        }
        fragment.setPageInfo(
            pagePos = position,
            fragmentReplacer = this
        )

        replace(position, fragment, isNotify)

        return fragment
    }

    override fun getItemCount(): Int {
        return PAGE_COUNT
    }

    override fun createFragment(position: Int): Fragment {
        return mapOfFragment[position] ?: replaceDef(position, false)
    }

    override fun getItemId(position: Int): Long {
        return mapOfFragment[position]?.pageId ?: super.getItemId(position)
    }

    override fun containsItem(itemId: Long): Boolean {
        var isContains = false
        mapOfFragment.values.forEach {
            if (it.pageId == itemId) {
                isContains = true
                return@forEach
            }
        }
        return isContains
    }

}