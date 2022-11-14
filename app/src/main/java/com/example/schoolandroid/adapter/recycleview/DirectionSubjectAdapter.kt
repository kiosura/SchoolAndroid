package com.example.schoolandroid.adapter.recycleview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolandroid.R
import com.example.schoolandroid.data.Filter
import com.example.schoolandroid.databinding.DirectionSubjectCardViewBinding


class DirectionSubjectAdapter: RecyclerView.Adapter<DirectionSubjectAdapter.FilterHolder>()  {
    private val filters = ArrayList<Filter>()

    class FilterHolder(card : View) : RecyclerView.ViewHolder(card){
        val binding = DirectionSubjectCardViewBinding.bind(card)
        fun bind(filter: Filter) = with(binding){
            filterbody.text = filter.text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.direction_subject_card_view, parent, false)
        return FilterHolder(view)
    }

    override fun onBindViewHolder(holder: FilterHolder, position: Int) {
        holder.bind(filters[position])
    }

    override fun getItemCount(): Int {
        return filters.size
    }

    fun addFilter(filter: Filter){
        filters.add(filter)
        notifyDataSetChanged()
    }
    fun addFilter(filter: List<Filter>){
        filters.addAll(filter)
        notifyDataSetChanged()
    }
}
