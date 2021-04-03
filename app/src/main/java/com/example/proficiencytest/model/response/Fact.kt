package com.example.proficiencytest.model.response


import androidx.room.Entity
import com.google.gson.annotations.SerializedName

data class Fact(
    @SerializedName("rows")
    var rows: List<Row>?,
    @SerializedName("title")
    var title: String?
)