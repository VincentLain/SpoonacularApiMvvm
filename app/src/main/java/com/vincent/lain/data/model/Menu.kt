package com.vincent.lain.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "menu_table")
data class Menu(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    var id: Int? = null,
    @ColumnInfo(name = "title")
    @SerializedName("title")
    @Expose
    var title: String? = null,

    @ColumnInfo(name = "restaurant_chain")
    @SerializedName("restaurantChain")
    @Expose
    var restaurantChain: String? = null,

    @ColumnInfo(name = "image")
    @SerializedName("image")
    @Expose
    var image: String? = null,

    @ColumnInfo(name = "image_type")
    @SerializedName("imageType")
    @Expose
    var imageType: String? = null,

    @ColumnInfo(name = "readable_serving_size")
    @SerializedName("readableServingSize")
    @Expose
    var readableServingSize: String? = null,

    @ColumnInfo(name = "serving_size")
    @SerializedName("servingSize")
    @Expose
    var servingSize: String? = null
)