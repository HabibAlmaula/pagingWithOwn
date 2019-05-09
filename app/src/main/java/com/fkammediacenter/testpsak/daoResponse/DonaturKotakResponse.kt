package com.fkammediacenter.testpsak.daoResponse

import com.google.gson.annotations.SerializedName

data class DonaturKotakResponse(
    @SerializedName("donaturlist")
    val donaturlist: List<DonaturKotakList>?,
    @SerializedName("error")
    val error: Boolean?,
    @SerializedName("message")
    val message: String?
){
    constructor() : this(null, null, null)
}