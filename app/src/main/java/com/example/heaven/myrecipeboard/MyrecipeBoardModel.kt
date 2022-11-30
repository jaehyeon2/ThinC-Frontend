package com.example.heaven.myrecipeboard

data class MyrecipeBoardModel (
    val id : Long,
    val title : String = "",
    val ingredient : String = "",
    val process : String = "",
    val category : String = "",
    val owner : String = ""
)
