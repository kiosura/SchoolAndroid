package com.example.schoolandroid.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolandroid.R
import com.example.schoolandroid.adapter.recycleview.DirectionSubjectAdapter
import com.example.schoolandroid.data.Filter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FilterCoursesDialog : BottomSheetDialogFragment() {

    private lateinit var recycleViewDirection: RecyclerView
    private lateinit var recycleViewSubject: RecyclerView
    private val filer_direction_adapter = DirectionSubjectAdapter()
    private val filer_subject_adapter = DirectionSubjectAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.filter_courses_layout, container, false)

        view.findViewById<Button>(R.id.closefilter).setOnClickListener(){
            dialog?.cancel()
        };

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycleViewDirection = view.findViewById(R.id.recycledirection)
        with(recycleViewDirection){
            layoutManager = GridLayoutManager(view.context, 3)
            adapter = filer_direction_adapter
        }

        filer_direction_adapter.addFilter(Filter("direction1"))
        filer_direction_adapter.addFilter(listOf(Filter("direction2"), Filter("direction3")))

        for (i in 4..6){
            filer_direction_adapter.addFilter(Filter("direction" + i.toString()))
        }

        recycleViewSubject = view.findViewById(R.id.recyclesubject)
        with(recycleViewSubject){
            layoutManager = GridLayoutManager(view.context, 3)
            adapter = filer_subject_adapter
        }

        for (i in 1..6){
            filer_direction_adapter.addFilter(Filter("subject" + i.toString()))
        }

    }
}