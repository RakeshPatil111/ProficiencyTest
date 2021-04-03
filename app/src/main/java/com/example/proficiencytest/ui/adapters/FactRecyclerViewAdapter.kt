package com.example.proficiencytest.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.proficiencytest.databinding.ItemFactBinding
import com.example.proficiencytest.model.response.Row
import com.example.proficiencytest.util.FactDiffUtil

class FactRecyclerViewAdapter : ListAdapter<Row, FactRecyclerViewAdapter.FactViewHolder>(FactDiffUtil()) {


    inner class FactViewHolder(val view : ItemFactBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FactViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return FactViewHolder(
            ItemFactBinding.inflate(inflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FactViewHolder, position: Int) {
        var fact = getItem(position)
        holder.view.fact = fact
        fact.id = System.currentTimeMillis() + position

        if (getItem(position).imageHref.isNullOrEmpty()) {
            holder.view.ivFact.visibility = View.GONE
        }

        if (getItem(position).description.isNullOrBlank()) {
            holder.view.tvDescription.visibility = View.GONE
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}