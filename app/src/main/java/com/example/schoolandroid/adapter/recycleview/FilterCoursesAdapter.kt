package com.example.schoolandroid.adapter.recycleview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolandroid.R
import com.example.schoolandroid.data.Filter
import com.example.schoolandroid.data.Filters
import com.example.schoolandroid.databinding.DirectionSubjectCardViewBinding
import com.example.schoolandroid.interfaces.Listener


class FilterCoursesAdapter(val listener : Listener) : RecyclerView.Adapter<FilterCoursesAdapter.FilterHolder>()  {
    private lateinit var filters : Filters

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
        holder.binding.filterbody.setOnClickListener {
            listener.onClick(position)
        }
    }

    override fun getItemCount(): Int {
        return filters.size
    }

    fun getFilter(position: Int) : Filter {
        return filters.get(position)
    }

    fun addFilters(filter: Filters){
        filters = filter
        notifyDataSetChanged()
    }
}
