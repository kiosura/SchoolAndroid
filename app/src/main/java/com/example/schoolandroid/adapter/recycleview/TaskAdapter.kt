package com.example.schoolandroid.adapter.recycleview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolandroid.R
import com.example.schoolandroid.data.Task
import com.example.schoolandroid.databinding.TaskCardViewBinding

class TaskAdapter : RecyclerView.Adapter<TaskAdapter.TaskHolder>() {
    private val tasks = ArrayList<Task>()

    class TaskHolder(card : View) : RecyclerView.ViewHolder(card){
        val binding = TaskCardViewBinding.bind(card)
        fun bind(task: Task) = with(binding){
            taskBody.text = task.id.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_card_view, parent, false)
        return TaskHolder(view)
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        holder.bind(tasks[position])
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    fun addTask(task: Task){
        tasks.add(task)
        notifyDataSetChanged()
    }
    fun addTask(task: List<Task>){
        tasks.addAll(task)
        notifyDataSetChanged()
    }
}