package com.example.simplenotes

import android.view.View
import android.widget.TextView
import java.text.SimpleDateFormat

fun formatTime(timeInMillis : Long) : String{
    return SimpleDateFormat("EEEE MMM-dd-yyyy ' Time: ' HH:mm").format(timeInMillis).toString()
}

fun toggleVisibility(textView: TextView){
    if (textView.text.isEmpty()){
        textView.visibility = View.GONE
    } else {
        textView.visibility = View.VISIBLE
    }
}