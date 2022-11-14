package com.example.schoolandroid.adapter.recycleview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolandroid.R
import com.example.schoolandroid.data.File
import com.example.schoolandroid.databinding.FileCardViewBinding

class FilesAdapter : RecyclerView.Adapter<FilesAdapter.FileHolder>() {
    val files : ArrayList<File> = ArrayList()

    class FileHolder(card : View) : RecyclerView.ViewHolder(card){
        val binding = FileCardViewBinding.bind(card)
        fun bind(file : File) = with(binding) {
            fileName.text = file.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.file_card_view, parent, false)
        return FileHolder(view)
    }

    override fun onBindViewHolder(holder: FileHolder, position: Int) {
        holder.bind(files[position])
    }

    override fun getItemCount(): Int {
        return files.size
    }

    fun addFile(file : File){
        files.add(file)
        notifyDataSetChanged()
    }
}