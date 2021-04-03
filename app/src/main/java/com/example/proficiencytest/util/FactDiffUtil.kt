package com.example.proficiencytest.util

import androidx.recyclerview.widget.DiffUtil
import com.example.proficiencytest.model.response.Row

class FactDiffUtil : DiffUtil.ItemCallback<Row>() {
    override fun areItemsTheSame(oldItem: Row, newItem: Row): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: Row, newItem: Row): Boolean {
        return oldItem == newItem
    }
}