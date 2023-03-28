package com.jmv.makelist

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

//ViewHolder - visual appearance of the row
class TodoListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    /*add two properties so that you can change the actual
    text within that view.*/
    var listPositionTextView = itemView.findViewById<TextView>(R.id.itemNumber)
    var listTitleTextView = itemView.findViewById<TextView>(R.id.itemString)

}