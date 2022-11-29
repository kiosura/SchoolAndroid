package com.example.schoolandroid.adapter.recycleview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolandroid.R
import com.example.schoolandroid.data.TaskItem
import com.example.schoolandroid.data.Tasks
import com.example.schoolandroid.databinding.TaskCardViewBinding
import com.example.schoolandroid.interfaces.Listener

class TaskAdapter(private val listener : Listener, private val layout : Int) : RecyclerView.Adapter<TaskAdapter.TaskHolder>() {
    private var tasks = ArrayList<TaskItem>()

    class TaskHolder(card : View) : RecyclerView.ViewHolder(card) {
        val binding = TaskCardViewBinding.bind(card)
        fun bind(task: TaskItem) = with(binding) {
            taskBodyText.text = task.index.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        val view = LayoutInflater.from(parent.context).inflate(layout, null)
        return TaskHolder(view)
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        holder.bind(tasks[position])
        holder.itemView.findViewById<LinearLayout>(R.id.taskBody).setOnClickListener {
            listener.onClick(position)
        }
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    fun addTask(task: TaskItem){
        tasks.add(task)
        notifyDataSetChanged()
    }

    fun putTasks(list : Tasks?){
        if (list != null) tasks = list
        else tasks.clear()
    }
}