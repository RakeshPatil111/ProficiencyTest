package com.example.proficiencytest.model.response


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "facts"
)
data class Row(
    @SerializedName("description")
    var description: String?,
    @SerializedName("imageHref")
    var imageHref: String?,
    @PrimaryKey(autoGenerate = false)
    @SerializedName("title")
    var title: String
) {
    fun isNotNull() : Boolean{
        return this.title != null || this.description != null || this.imageHref != null
    }
}