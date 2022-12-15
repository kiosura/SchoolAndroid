package com.example.schoolandroid.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolandroid.R
import com.example.schoolandroid.adapter.recycleview.FilterCoursesAdapter
import com.example.schoolandroid.data.CourseCompose
import com.example.schoolandroid.data.Filter
import com.example.schoolandroid.data.FilterCompose
import com.example.schoolandroid.interfaces.FilterInt
import com.example.schoolandroid.interfaces.Listener
import com.google.android.flexbox.FlexDirection.ROW
import com.google.android.flexbox.FlexWrap.WRAP
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FilterCoursesDialog(val filter: FilterInt, val isMy : Boolean) : BottomSheetDialogFragment(), Listener {

    private lateinit var recycleViewDirection: RecyclerView
//    private lateinit var recycleViewSubject: RecyclerView
    private val filter_direction_adapter = FilterCoursesAdapter(this)
//    private val filer_subject_adapter = FilterCoursesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.filter_courses_layout, container, false)

        view.findViewById<TextView>(R.id.closefilter).setOnClickListener(){
            dialog?.cancel()
        };

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycleViewDirection = view.findViewById(R.id.recycledirection)
        with(recycleViewDirection){
            layoutManager = FlexboxLayoutManager(context, ROW, WRAP)
            adapter = filter_direction_adapter
        }

        filter_direction_adapter.addFilters(FilterCompose.parseTags())


//        recycleViewSubject = view.findViewById(R.id.recyclesubject)
//        with(recycleViewSubject){
//            layoutManager = FlexboxLayoutManager(context, ROW, WRAP)
//            adapter = filer_subject_adapter
//        }
//
//        for (i in 1..6){
//            filer_subject_adapter.addFilter(Filter("subject" + i.toString()))
//        }

    }

    override fun onClick(position: Int) {
        val filter_item = filter_direction_adapter.getFilter(position)
        val indices =CourseCompose.getWithTag(filter_item, isMy)
        filter.change(indices)
    }
}