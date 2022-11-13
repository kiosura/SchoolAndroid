package com.example.schoolandroid.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.schoolandroid.R
import com.example.schoolandroid.data.Push
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FilterCoursesDialog : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.filte_courses_layout, container, false)

        view.findViewById<Button>(R.id.closefilter).setOnClickListener(){
            dialog?.cancel()
        };

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        recycleView = view.findViewById(R.id.recyclePushes)
//        with(recycleView){
//            layoutManager = LinearLayoutManager(view.context)
//            adapter = push_adapter
//        }
//
//        push_adapter.addPush(Push("push1"))
//        push_adapter.addPush(listOf(Push("push2"), Push("push3")))
//
//        for (i in 4..200){
//            push_adapter.addPush(Push("push" + i.toString()))
//        }
    }
}