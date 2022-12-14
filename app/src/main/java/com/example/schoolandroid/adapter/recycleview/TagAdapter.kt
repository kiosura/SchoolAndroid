package com.example.schoolandroid.adapter.recycleview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolandroid.R
import com.example.schoolandroid.data.Tag
import com.example.schoolandroid.data.Tags
import com.example.schoolandroid.databinding.TagCardBinding

class TagAdapter : RecyclerView.Adapter<TagAdapter.TagHolder>() {
    private var tags : Tags = Tags()

    class TagHolder(card : View) : RecyclerView.ViewHolder(card) {
        val binding = TagCardBinding.bind(card)

        fun bind(tag : Tag) = with(binding){
            tagName.text = tag.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tag_card, parent, false)
        return TagHolder(view)
    }

    override fun onBindViewHolder(holder: TagHolder, position: Int) {
        holder.bind(tags[position])
    }

    override fun getItemCount(): Int {
        return tags.size
    }

    fun addTags(list : Tags?) {
        if (list != null) tags = list
        else tags.clear()
        notifyDataSetChanged()
    }
}