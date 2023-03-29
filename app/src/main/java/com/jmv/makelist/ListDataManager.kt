package com.jmv.makelist

import android.content.Context
import androidx.preference.PreferenceManager

class ListDataManager(private val context: Context) {

    fun saveList(list: TaskList) {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context).edit()
        sharedPrefs.putStringSet(list.name, list.tasks.toHashSet())
        sharedPrefs.apply()
    }

    fun readLists(): ArrayList<TaskList> {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
        //contents contain a map of keys and values
        val contents = sharedPrefs.all
        val taskLists = ArrayList<TaskList>()

        //loop through the keys
        for (taskList in contents) {
            //get the saved hashset and convert it into an ArrayList
            val taskItems = ArrayList(taskList.value as HashSet<String>)
            //create a task list from it
            val list = TaskList(taskList.key, taskItems)
            //add it to the ArrayList
            taskLists.add(list)
        }

        return taskLists

    }

}