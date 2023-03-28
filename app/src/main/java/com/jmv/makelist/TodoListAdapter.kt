package com.jmv.makelist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TodoListAdapter : RecyclerView.Adapter<TodoListViewHolder>() {

    private val todoLists = arrayOf("Android Development", "House Work", "Errands", "Shopping")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.todo_list_view_holder, parent, false)

        /*
       todo_list_view_holder - This is your cell layout
       root ViewGroup - parent (A ViewGroup is simply a view that contains other views)
       false - you pass in the ViewGroup provided to the method but you don't want to automatically
       attach the ViewHolder to it. That's the RecyclerView's job.
        */

        //create your ViewHolder from inflated view
        return TodoListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return todoLists.size
    }

    //binding the data
    override fun onBindViewHolder(holder: TodoListViewHolder, position: Int) {

        holder.listPositionTextView.text = (position + 1).toString()
        holder.listTitleTextView.text = todoLists[position]

    }

}