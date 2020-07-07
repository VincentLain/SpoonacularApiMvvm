package com.vincent.lain.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class MenuResponse (
    @SerializedName("totalMenuItems")
    @Expose
    var totalMenuItems: Int? = null,

    @SerializedName("type")
    @Expose
    var type: String? = null,

    @SerializedName("offset")
    @Expose
    var offset: Int? = null,

    @SerializedName("number")
    @Expose
    var number: Int? = null,

    @SerializedName("menuItems")
    @Expose
    var menuItems: List<Menu>? = null
)