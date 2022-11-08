package com.example.schoolandroid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolandroid.R
import com.example.schoolandroid.data.Push
import com.example.schoolandroid.databinding.CourseCardViewBinding
import com.example.schoolandroid.databinding.PushCardViewBinding

class PushAdapter : RecyclerView.Adapter<PushAdapter.PushHolder>() {
    private val pushes = ArrayList<Push>()

    class PushHolder(card : View) : RecyclerView.ViewHolder(card){
        val binding = PushCardViewBinding.bind(card)
        fun bind(push: Push) = with(binding){
            pushBody.text = push.text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PushHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.push_card_view, parent, false)
        return PushHolder(view)
    }

    override fun onBindViewHolder(holder: PushHolder, position: Int) {
        holder.bind(pushes[position])
    }

    override fun getItemCount(): Int {
        return pushes.size
    }

    fun addPush(push: Push){
        pushes.add(push)
        notifyDataSetChanged()
    }
    fun addPush(push : List<Push>){
        pushes.addAll(push)
        notifyDataSetChanged()
    }
}