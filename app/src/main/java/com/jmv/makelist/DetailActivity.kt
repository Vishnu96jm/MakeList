package com.jmv.makelist

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DetailActivity : AppCompatActivity() {

    lateinit var list: TaskList
    lateinit var taskListRecyclerView: RecyclerView
    lateinit var addTaskButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        list = intent.getParcelableExtra<TaskList>(MainActivity.INTENT_LIST_KEY) as TaskList
      //  list = intent.getParcelableExtra<TaskList>(MainActivity.INTENT_LIST_KEY, TaskList::class.java) as TaskList
        title = list.name

        taskListRecyclerView = findViewById(R.id.task_list_recyclerview)
        taskListRecyclerView.layoutManager = LinearLayoutManager(this)
        taskListRecyclerView.adapter = TaskListAdapter(list)

        addTaskButton = findViewById(R.id.add_task_button)
        addTaskButton.setOnClickListener {
            showCreateTaskDialog()
        }
    }

    //send the results in onBackPressed()
    override fun onBackPressed() {
        //create a Bundle to store things
        val bundle = Bundle()
        bundle.putParcelable(MainActivity.INTENT_LIST_KEY, list)

        //create an Intent
        val intent = Intent()
       // intent.putExtra(MainActivity.INTENT_LIST_KEY, list)
        intent.putExtras(bundle)
        setResult(Activity.RESULT_OK, intent)
        //super goes last for this method
        super.onBackPressed()
    }

    private fun showCreateTaskDialog() {
        val taskEditText = EditText(this)
        taskEditText.inputType = InputType.TYPE_CLASS_TEXT
        AlertDialog.Builder(this)
            .setTitle(R.string.task_to_add)
            .setView(taskEditText)
            .setPositiveButton(R.string.add_task, object : DialogInterface.OnClickListener{
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    val task = taskEditText.text.toString()
                    list.tasks.add(task)
                    p0?.dismiss()
                }
            })
            /*.setPositiveButton(R.string.add_task) {
                    dialog, _ ->
                val task = taskEditText.text.toString()
                list.tasks.add(task)
                dialog.dismiss()
            }*/
            .create()
            .show()

    }
}