package com.arif.generatepdffile

data class PdfData(
    val name:String,
    val amount:String,
    val number:String,
    val narration:String,
    val type:String,
    val location:String,
    val transactionTime:String,
    var shouldDownload:Boolean=false,
)
